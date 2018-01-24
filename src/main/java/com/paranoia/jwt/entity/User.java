package com.paranoia.jwt.entity;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/19 15:17
 */
public class User {

    private String app_id;
    private String app_secret;

    public User(String app_id, String app_secret) {
        this.app_id = app_id;
        this.app_secret = app_secret;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
    }
}
