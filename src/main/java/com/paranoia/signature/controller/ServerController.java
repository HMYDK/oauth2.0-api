package com.paranoia.signature.controller;

import com.alibaba.fastjson.JSON;
import com.paranoia.signature.dto.Person;
import com.paranoia.sys.response.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/26 10:40
 */
@RestController
@RequestMapping("/sign")
public class ServerController {

    @RequestMapping(value = "/demo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Response demo(@RequestBody String jsonString) {
        System.out.println("服务端接收到的请求参数 = " + jsonString);
        Person person = JSON.parseObject(jsonString, Person.class);
        System.out.println("person = " + person);
        return Response.ok();
    }
}
