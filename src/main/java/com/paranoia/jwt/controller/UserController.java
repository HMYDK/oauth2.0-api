package com.paranoia.jwt.controller;

import com.alibaba.fastjson.JSON;
import com.paranoia.jwt.config.JwtConfigByProperties;
import com.paranoia.jwt.doman.TokenDTO;
import com.paranoia.jwt.entity.User;
import com.paranoia.jwt.util.JwtUtil;
import com.paranoia.oauth.service.OauthPlugInService;
import com.paranoia.sys.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/19 15:14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    OauthPlugInService oauthPlugInService;

    @Autowired
    JwtConfigByProperties jwtConfigByProperties;

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Response login(@RequestBody String jsonString) {
        User user = JSON.parseObject(jsonString,User.class);
        if (ObjectUtils.isEmpty(user)){
            return Response.error("用户名或者密码不能为空");
        }
        logger.info(user.toString());

        if (oauthPlugInService.findByAppIdAndAppSecretAndStatus(user.getApp_id(),user.getApp_secret(),0)) {
            Response response = Response.ok();
            String token = JwtUtil.sign(new TokenDTO(user.getApp_id()),jwtConfigByProperties);
            if (token != null) {
                response.put("token", token);
            }
            return response;
        }
        return Response.error("用户名或者密码错误");
    }


}
