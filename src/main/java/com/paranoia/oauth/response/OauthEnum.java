package com.paranoia.oauth.response;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/14 15:01
 */
public enum OauthEnum {
    CESHI(1,"CE");


    //-------------------------------------------------------------

    OauthEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    private int key ;

    private String value ;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
