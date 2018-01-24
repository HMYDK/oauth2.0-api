package com.paranoia.api.jwt.controller;

import com.paranoia.sys.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/19 15:14
 */
@RestController
@RequestMapping("/shejiin")
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping(value = "/api",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Response api() {
        return Response.ok("你好~~~");
    }

}
