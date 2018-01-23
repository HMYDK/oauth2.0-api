package com.paranoia.jwt.doman;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/23 10:53
 */
public class TokenDTO {
    private String appId ;

    public TokenDTO() {
    }

    public TokenDTO(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
