package com.paranoia.oauth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/18 11:44
 */
@Table
@Entity
public class OauthThird {
    
    @Id
    @GeneratedValue
    private Integer id ;
    private String uid ;
    private String name ;
    private String logogram ;
    private String description ;
    private Integer status ;

    public OauthThird() {
    }

    @Override
    public String toString() {
        return "OauthThird{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", logogram='" + logogram + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogogram() {
        return logogram;
    }

    public void setLogogram(String logogram) {
        this.logogram = logogram;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
