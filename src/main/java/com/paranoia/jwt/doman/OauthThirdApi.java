package com.paranoia.jwt.doman;

import javax.persistence.*;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/23 9:54
 */
@Entity
@Table
public class OauthThirdApi {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "third_uid")
    private String thirdUid;
    private String name;
    private String root;
    private Integer status;

    public OauthThirdApi() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThirdUid() {
        return thirdUid;
    }

    public void setThirdUid(String thirdUid) {
        this.thirdUid = thirdUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
