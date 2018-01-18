package com.paranoia.modules.designercase.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranoia.oauth.util.HttpUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/17 15:25
 */
@RestController
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @ApiOperation(value = "打招呼", notes = "姓名")
    @GetMapping("/hi")
    public String sayHi(@RequestParam("name") String name) {
        return "你好啊：" + name;
    }

    @ApiOperation(value = "打招呼", notes = "姓名")
    @RequestMapping(value = "/hello", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean hello(@RequestBody String jsonString, HttpServletRequest request) {
        Param param = FastJsonUtil.toBean(jsonString, Param.class);
        logger.info("这是资源服务器接收到的客户端请求的参数：" + param);

        if (validate(param,request)) {
            return new ResultBean(10001, "已经接受到你的name:" + param.getName());
        }
        return new ResultBean(22222, "不合法的请求");
    }

    private boolean validate(Param param, HttpServletRequest request)  {
        String requestUrl = request.getRequestURI();
        logger.info("requestUrl:__________" + requestUrl);
        ValaditeTokenBean valaditeTokenBean = new ValaditeTokenBean(param.getAppId(),"oauth",requestUrl);
        String url = "http://localhost:8888/api/oauth2/verifyToken";
        String jsonString = null;
        try {
            jsonString = new ObjectMapper().writeValueAsString(valaditeTokenBean);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = HttpUtils.doPostStr(url, jsonString);
        logger.info(jsonObject.toString());
        String code = jsonObject.getString("code");
        if ("0".equals(code)){
            return true;
        }
        return false;
    }

    class ValaditeTokenBean{
        private String appId ;

        private String appName ;

        private String url ;

        public ValaditeTokenBean() {
        }

        public ValaditeTokenBean(String appId, String appName, String url) {
            this.appId = appId;
            this.appName = appName;
            this.url = url;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
