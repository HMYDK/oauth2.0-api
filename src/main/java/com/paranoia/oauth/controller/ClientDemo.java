package com.paranoia.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranoia.modules.designercase.controller.Param;
import com.paranoia.oauth.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/15 13:56
 */
@Controller
@RequestMapping("/client")
public class ClientDemo {
    Logger logger = LoggerFactory.getLogger(ClientDemo.class);

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping(value = "/login", produces = {"text/plain;charset=UTF-8"})
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("开始授权");

        String backUrl = "http://localhost:8888/api/client/callBack";

        String url = "http://localhost:8888/api/oauth2/authorize?"
                + "appid=" + "e69618f8249b47b39b8e52415c0fc117"
                + "&redirect_uri=" + backUrl;

        System.out.println("url = " + url);

        resp.sendRedirect(url);
    }

    @RequestMapping(value = "/callBack", method = RequestMethod.GET)
    protected void callBack(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("------------进入redirectUrl----------------");
        String code = req.getParameter("code");

        String url = "http://localhost:8888/api/oauth2/access_token?"
                + "appid=" + "e69618f8249b47b39b8e52415c0fc117"
                + "&secret=" + "8c957a4b7f1f4967afb638f6c453d76e"
                + "&code=" + code
                + "&grant_type=authorization_code";

        JSONObject jsonObject = HttpUtils.doGetStr(url);
        String token = jsonObject.getString("access_token");
        System.out.println("code = " + code);

        System.out.println("jsonObject = " + jsonObject.toString());
    }

    @RequestMapping(value = "/getSource", method = RequestMethod.GET)
    protected void getSource() throws ServletException, IOException {
        logger.info("客户端开始进行第三方资源请求");
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String token = operations.get("e69618f8249b47b39b8e52415c0fc117token");
        logger.info("客户端请求资源时拿到的ｔｏｋｅｎ：" + token);
        String url = "http://localhost:8888/api/hello";
        Param param = new Param("张三", token,"e69618f8249b47b39b8e52415c0fc117");
        ObjectMapper mapper = new ObjectMapper();
        String jsonParam = mapper.writeValueAsString(param);
        logger.info("参数json:" + jsonParam);
        JSONObject jsonObject = HttpUtils.doPostStr(url, jsonParam);

        System.out.println("jsonObject = " + jsonObject.toString());
    }
}
