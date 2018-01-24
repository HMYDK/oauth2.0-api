package com.paranoia.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/24 14:41
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public  class JwtConfigByProperties {

    private String header;

    private String secret;

    private Long expiration;

    private String ignore;

    private String tokenHead;


    public JwtConfigByProperties() {
    }

    public JwtConfigByProperties(String header, String secret, Long expiration, String ignore, String tokenHead) {
        this.header = header;
        this.secret = secret;
        this.expiration = expiration;
        this.ignore = ignore;
        this.tokenHead = tokenHead;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }
}
