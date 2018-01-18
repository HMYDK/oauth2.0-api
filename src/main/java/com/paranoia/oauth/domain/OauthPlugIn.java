package com.paranoia.oauth.domain;


import javax.persistence.*;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/17 16:27
 */
@Entity
@Table
public class OauthPlugIn {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "team_id")
    private String teamId ;
    @Column(name = "app_id")
    private String appId ;
    @Column(name = "app_secret")
    private String appSecret ;
    private Integer status  ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
