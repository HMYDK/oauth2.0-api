package com.paranoia.modules.sys.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/15 10:26
 */
public class SysLogEntity implements Serializable{
    private static final long serialVersionUID = 3964946269455680501L;

    private Long id;
    //用户名
    private String username;
    //用户操作
    private String operation;
    //请求方法
    private String method;
    //请求参数
    private Object[] params;
    //执行时长(毫秒)
    private Long time;
    //IP地址
    private String ip;
    //创建时间
    private Date createDate;

    @Override
    public String toString() {
        return "SysLogEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", operation='" + operation + '\'' +
                ", method='" + method + '\'' +
                ", params=" + Arrays.toString(params) +
                ", time=" + time +
                ", ip='" + ip + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
