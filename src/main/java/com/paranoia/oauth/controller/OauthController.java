package com.paranoia.oauth.controller;

import com.paranoia.annotation.SysLog;
import com.paranoia.oauth.entity.TokenEntity;
import com.paranoia.oauth.response.OauthEnum;
import com.paranoia.oauth.response.OauthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
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

    /**
     * 验证appId和回调域名
     *
     * @param appId
     * @param redirectUri
     */
    @SysLog("验证appId和回调域名")
    @GetMapping(value = "/authorize")
    public OauthResponse oauthUser(@RequestParam("appid") String appId,
                                   @RequestParam("redirect_uri") String redirectUri,
                                   HttpServletResponse response) {

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        //判断appid是否存在，若存在则校验回调域是否正确
        String sqlAppid = "123";
        String sqlUri = "localhost";
        //拼装校验参数
        Map<String, String> param = new HashMap<>();
        param.put("appId", appId);
        param.put("redirectUri", redirectUri);
        param.put("sqlAppid", sqlAppid);
        param.put("sqlUri", sqlUri);
        //参数校验，若参数有问题则将指定的参数校验码返回
        OauthResponse oauthResponse = this.paramVerify(param);
        if (0 != (int) oauthResponse.get("code")) {
            return oauthResponse;
        }
        //FINISH code 需要放到redis中，有效时间五分钟   appid-code
        int code = UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        if (redisTemplate.hasKey(appId)){
            redisTemplate.delete(appId);
        }
        operations.set(appId, String.valueOf(code), 5, TimeUnit.MINUTES);
        try {
            response.sendRedirect(redirectUri + "?code=" + String.valueOf(code));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return OauthResponse.ok();
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

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        Map<String, String> param = new HashMap<>();
        param.put("appId", appId);
        param.put("secret", secret);
        param.put("code", code);
        param.put("redisCode", operations.get(appId));
        param.put("grantType", grantType);
        param.put("sqlAppid", "123");
        param.put("sqlSecret", "456");
        OauthResponse oauthResponse = this.paramVerifyForGetToken(param);
        if (0 != (int) oauthResponse.get("code")) {
            return oauthResponse;
        }
        /**
         * TODO token从数据库查询
         *          1：存在
         *                 有效：直接返回
         *                 过期：重新生成&替换数据库数据
         *          2：不存在
         *                  生成一个
         */
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        TokenEntity tokenEntity = new TokenEntity(token, 3600);
        return OauthResponse.ok(tokenEntity);
    }


    private OauthResponse paramVerify(Map<String, String> param) {
        if (StringUtils.isEmpty(param.get("appId"))) {
            return OauthResponse.error(OauthEnum.APPID_IS_NULL);
        }
        if (StringUtils.isEmpty(param.get("redirectUri"))) {
            return OauthResponse.error(OauthEnum.REDIRECR_IS_NULL);
        }
        if (!param.get("sqlAppid").equals(param.get("appId"))) {
            return OauthResponse.error(OauthEnum.ACCESS_FAIL);
        }
        if (!param.get("redirectUri").contains(param.get("sqlUri"))) {
            return OauthResponse.error(OauthEnum.REDIRECR_NOT_REGIST);
        }
        return OauthResponse.ok();
    }

    private OauthResponse paramVerifyForGetToken(Map<String, String> param) {
        if (StringUtils.isEmpty(param.get("appId"))) {
            return OauthResponse.error(OauthEnum.APPID_IS_NULL);
        }
        if (StringUtils.isEmpty(param.get("secret"))) {
            return OauthResponse.error(OauthEnum.SECRET_IS_NULL);
        }
        if (!param.get("sqlAppid").equals(param.get("appId"))) {
            return OauthResponse.error(OauthEnum.ACCESS_FAIL);
        }
        if (!param.get("sqlSecret").equals(param.get("secret"))) {
            return OauthResponse.error(OauthEnum.SECRET_IS_WRONG);
        }
        if (!"authorization_code".equals(param.get("grantType"))) {
            return OauthResponse.error(OauthEnum.GRANT_TYPE_IS_WRONG);
        }
        //FINISH redis中拿取上一步的code  鉴别是否过期
        logger.info(String.format("redisCode = %s", param.get("redisCode")));
        if (StringUtils.isEmpty(param.get("redisCode"))) {
            return OauthResponse.error(OauthEnum.CODE_LOSE_EFFICACY);
        }
        if (!param.get("redisCode").equals(param.get("code"))) {
            return OauthResponse.error(OauthEnum.CODE_IS_WRONG);
        }
        return OauthResponse.ok();
    }
}
