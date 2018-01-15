package com.paranoia.oauth.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/14 14:58
 */
public class OauthResponse extends HashMap<String ,Object>{

    private static final long serialVersionUID = -2244021038461770624L;

    public OauthResponse() {
        put("code", 0);
        put("msg", "操作成功");
    }

    public static OauthResponse error() {
        return error(1, "操作失败");
    }

    public static OauthResponse error(String msg) {
        return error(500, msg);
    }

    public static OauthResponse error(int code, String msg) {
        OauthResponse r = new OauthResponse();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static OauthResponse ok(String msg) {
        OauthResponse r = new OauthResponse();
        r.put("msg", msg);
        return r;
    }

    public static OauthResponse ok(Map<String, Object> map) {
        OauthResponse r = new OauthResponse();
        r.putAll(map);
        return r;
    }

    public static OauthResponse ok() {
        return new OauthResponse();
    }

    @Override
    public OauthResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
