package com.paranoia.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.paranoia.oauth.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @GetMapping(value = "/login", produces = {"text/plain;charset=UTF-8"})
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("开始授权");

        String backUrl = "http://localhost:8080/api/client/callBack";

        String url = "http://localhost:8080/api/oauth2/authorize?"
                + "appid=" + "123"
                + "&redirect_uri=" + backUrl;

        System.out.println("url = " + url);

        resp.sendRedirect(url);
    }

    @RequestMapping(value = "/callBack",method = RequestMethod.GET)
    protected void callBack(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("------------进入redirectUrl----------------");
        String code = req.getParameter("code");

        String url = "http://localhost:8080/api/oauth2/access_token?"
                + "appid=" + "123"
                + "&secret=" + "456"
                + "&code=" + code
                + "&grant_type=authorization_code";

        JSONObject jsonObject = HttpUtils.doGetStr(url);
        String token = jsonObject.getString("access_token");
        System.out.println("code = " + code);
        System.out.println("token = " + token);
        System.out.println("jsonObject = " + jsonObject.toString());
    }
}
