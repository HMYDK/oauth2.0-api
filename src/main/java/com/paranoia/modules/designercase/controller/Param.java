package com.paranoia.modules.designercase.controller;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/17 18:52
 */
public class Param {
    private String name ;
    private String token ;
    private String appId ;



    public Param() {
    }

    public Param(String name, String token, String appId) {
        this.name = name;
        this.token = token;
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "Param{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
