package com.paranoia.oauth.domain;

import javax.persistence.*;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/18 11:36
 */
@Entity
@Table
public class OauthPlugInDetails {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "app_id")
    private String appId ;
    @Column(name = "third_uid")
    private String thirdUid ;
    private String method ;
    private Integer status;


    public OauthPlugInDetails() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getThirdUid() {
        return thirdUid;
    }

    public void setThirdUid(String thirdUid) {
        this.thirdUid = thirdUid;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
