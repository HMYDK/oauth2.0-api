package com.paranoia.oauth.controller;

import com.paranoia.oauth.domain.OauthPlugIn;
import com.paranoia.oauth.domain.OauthThird;
import com.paranoia.oauth.entity.TokenEntity;
import com.paranoia.oauth.response.OauthEnum;
import com.paranoia.oauth.service.OauthPlugInDetailsService;
import com.paranoia.oauth.service.OauthPlugInService;
import com.paranoia.oauth.service.OauthThirdService;
import com.paranoia.source.controller.FastJsonUtil;
import com.paranoia.sys.annotation.SysLog;
import com.paranoia.sys.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * todo 参数顺序强制校验
 *
 * @author PARANOIA_ZK
 * @date 2018/1/15 10:55
 */
@RestController
@RequestMapping("/oauth2")
public class OauthController {

    Logger logger = LoggerFactory.getLogger(OauthController.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    OauthPlugInService oauthPlugInService;

    @Autowired
    OauthThirdService oauthThirdService;

    @Autowired
    OauthPlugInDetailsService oauthPlugInDetailsService;


    /**
     * 验证appId和回调域名
     *
     * @param appId
     * @param redirectUri
     */
    @SysLog("验证appId和回调域名")
    @GetMapping(value = "/authorize")
    public Response oauthUser(@RequestParam("appid") String appId,
                              @RequestParam("redirect_uri") String redirectUri,
                              HttpServletResponse response) {

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        //判断appid是否存在，若存在则校验回调域是否正确
        String sqlAppid = "e69618f8249b47b39b8e52415c0fc117";
        String sqlUri = "localhost";
        //拼装校验参数
        Map<String, String> param = new HashMap<>();
        param.put("appId", appId);
        param.put("redirectUri", redirectUri);
        param.put("sqlAppid", sqlAppid);
        param.put("sqlUri", sqlUri);
        //参数校验，若参数有问题则将指定的参数校验码返回
        Response oauthResponse = this.paramVerify(param);
        if (0 != (int) oauthResponse.get("code")) {
            return oauthResponse;
        }
        //FINISH code 需要放到redis中，有效时间五分钟   appid-code
        int code = UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        if (redisTemplate.hasKey(appId)) {
            redisTemplate.delete(appId);
        }
        operations.set(appId, String.valueOf(code), 5, TimeUnit.MINUTES);
        try {
            response.sendRedirect(redirectUri + "?code=" + String.valueOf(code));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok();
    }

    /**
     * 防止code被截取冒用，客户端需要再次进行验证
     *
     * @return
     */
    @SysLog("验证code")
    @GetMapping(value = "/access_token")
    public Object getToken(@RequestParam("appid") String appId,
                           @RequestParam("secret") String secret,
                           @RequestParam("code") String code,
                           @RequestParam("grant_type") String grantType) {

        OauthPlugIn oauthPlugIn = oauthPlugInService.getByAppId(appId);

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        Map<String, String> param = new HashMap<>();
        param.put("appId", appId);
        param.put("secret", secret);
        param.put("code", code);
        param.put("redisCode", operations.get(appId));
        param.put("grantType", grantType);
        if (ObjectUtils.isEmpty(oauthPlugIn)){
            param.put("sqlAppid", "");
            param.put("sqlSecret", "");
        }else {
            param.put("sqlAppid", oauthPlugIn.getAppId());
            param.put("sqlSecret", oauthPlugIn.getAppSecret());
        }
        Response oauthResponse = this.paramVerifyForGetToken(param);
        if (0 != (int) oauthResponse.get("code")) {
            return oauthResponse;
        }
        if (redisTemplate.hasKey(appId + "token")) {
            redisTemplate.delete(appId + "token");
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        operations.set(appId + "token", token, 30, TimeUnit.DAYS);
        //todo  是否持久化用户token?
        //TODO  提醒用户最好是29天重新授权一次
        TokenEntity tokenEntity = new TokenEntity(token, 30);
        return Response.ok(tokenEntity);
    }

    @SysLog("鉴权")
    @RequestMapping(value = "/verifyToken", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Response verify(@RequestBody String jsonString) {
        //todo 转化异常全局处理
        Map<String,String> paramMap = FastJsonUtil.stringToCollect(jsonString);

        logger.info("oauth接收到的志愿服务器验证token的参数：" + paramMap);

        Response oauthResponse =  this.paramVerifyForToken(paramMap);
        if (0 != (int) oauthResponse.get("code")) {
            return oauthResponse;
        }
        OauthThird oauthThird = oauthThirdService.getThirdByNameAndStatus(paramMap.get("appName"),0);
        if (ObjectUtils.isEmpty(oauthThird)){
            return Response.error(OauthEnum.THIRD_STATUS_FAIL);
        }
        logger.info(oauthThird.toString());
        List<String> methodList = oauthPlugInDetailsService.getDetailsByAppIdAndLogogram(paramMap.get("appId"), oauthThird.getUid(), 0);

        if (methodList.isEmpty()){
            return Response.error(OauthEnum.THIRD_DONOT_HAS_AUTHORITY);
        }
        if (!methodList.contains(paramMap.get("url"))){
            return Response.error(OauthEnum.THIRD_DONOT_HAS_THIS_AUTHORITY);
        }
        logger.info(methodList.toString());
        return Response.ok();
    }


    /**
     * 授权参数校验
     * @param param
     * @return
     */
    private Response paramVerify(Map<String, String> param) {
        if (StringUtils.isEmpty(param.get("appId"))) {
            return Response.error(OauthEnum.APPID_IS_NULL);
        }
        if (StringUtils.isEmpty(param.get("redirectUri"))) {
            return Response.error(OauthEnum.REDIRECR_IS_NULL);
        }
        if (!param.get("sqlAppid").equals(param.get("appId"))) {
            return Response.error(OauthEnum.ACCESS_FAIL);
        }
        if (!param.get("redirectUri").contains(param.get("sqlUri"))) {
            return Response.error(OauthEnum.REDIRECR_NOT_REGIST);
        }
        return Response.ok();
    }

    /**
     * 认证参数校验
     * @param param
     * @return
     */
    private Response paramVerifyForGetToken(Map<String, String> param) {
        if (StringUtils.isEmpty(param.get("appId"))) {
            return Response.error(OauthEnum.APPID_IS_NULL);
        }
        if (StringUtils.isEmpty(param.get("secret"))) {
            return Response.error(OauthEnum.SECRET_IS_NULL);
        }
        if (!param.get("sqlAppid").equals(param.get("appId"))) {
            return Response.error(OauthEnum.ACCESS_FAIL);
        }
        if (!param.get("sqlSecret").equals(param.get("secret"))) {
            return Response.error(OauthEnum.SECRET_IS_WRONG);
        }
        if (!"authorization_code".equals(param.get("grantType"))) {
            return Response.error(OauthEnum.GRANT_TYPE_IS_WRONG);
        }
        //FINISH redis中拿取上一步的code  鉴别是否过期
        logger.info(String.format("redisCode = %s", param.get("redisCode")));
        if (StringUtils.isEmpty(param.get("redisCode"))) {
            return Response.error(OauthEnum.CODE_LOSE_EFFICACY);
        }
        if (!param.get("redisCode").equals(param.get("code"))) {
            return Response.error(OauthEnum.CODE_IS_WRONG);
        }
        return Response.ok();
    }

    /**
     * 鉴权参数校验
     * @param param
     * @return
     */
    private Response paramVerifyForToken(Map<String, String> param) {
        String appId = param.get("appId");
        if (StringUtils.isEmpty(appId)){
            return Response.error(OauthEnum.APPID_IS_NULL);
        }
        // 如果持久化用户token，那么这里的逻辑需要修改
        if (!redisTemplate.hasKey(appId+"token")){
            return Response.error(OauthEnum.THIRD_TOKEN_LOSE_EFFICACY);
        }
        if (StringUtils.isEmpty(param.get("appName"))){
            return Response.error(OauthEnum.THIRD_APP_NAME_IS_NULL);
        }
        return Response.ok();
    }
}
