package com.paranoia.api.jwt.controller;

import com.alibaba.fastjson.JSON;
import com.paranoia.api.jwt.entity.User;
import com.paranoia.jwt.util.JwtUtil;
import com.paranoia.sys.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Response login(@RequestBody String jsonString) {
        User user = JSON.parseObject(jsonString,User.class);
        if (ObjectUtils.isEmpty(user)){
            return Response.error("用户名或者密码不能为空");
        }
        logger.info(user.toString());
        if ("zhangsan".equals(user.getUsername()) && "123".equals(user.getPassword())) {
            Response response = Response.ok();
            response.put("user", user);
            String token = JwtUtil.sign(user);
            if (token != null) {
                response.put("token", token);
            }
            return response;
        }
        return Response.error("用户名或者密码错误");
    }

}
