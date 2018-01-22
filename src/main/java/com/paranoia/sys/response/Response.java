package com.paranoia.sys.response;

import com.paranoia.jwt.response.JwtEnum;
import com.paranoia.oauth.response.OauthEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/14 14:58
 */
public class Response extends HashMap<String ,Object>{

    private static final long serialVersionUID = -2244021038461770624L;

    public Response() {
        put("code", 0);
        put("msg", "操作成功");
    }

    public static Response error() {
        return error(1, "操作失败");
    }

    public static Response error(String msg) {
        return error(500, msg);
    }

    public static Response error(int code, String msg) {
        Response r = new Response();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    //todo  这里抽一个公共enum出来

    public static Response error(OauthEnum code) {
        Response r = new Response();
        r.put("code", code.getKey());
        r.put("msg", code.getValue());
        return r;
    }

    public static Response error(JwtEnum code) {
        Response r = new Response();
        r.put("code", code.getKey());
        r.put("msg", code.getValue());
        return r;
    }

    public static Response ok(String msg) {
        Response r = new Response();
        r.put("msg", msg);
        return r;
    }

    public static Response ok(Map<String, Object> map) {
        Response r = new Response();
        r.putAll(map);
        return r;
    }

    public static Response ok() {
        return new Response();
    }

    public static Object ok(Object object) {
        return object;
    }

    @Override
    public Response put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
