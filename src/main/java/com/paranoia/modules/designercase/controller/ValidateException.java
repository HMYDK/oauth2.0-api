package com.paranoia.modules.designercase.controller;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/17 19:03
 */
public class ValidateException extends Throwable {

    private static final long serialVersionUID = 885436030318538404L;

    private int key ;

    private String value ;


    public ValidateException() {
    }

    public ValidateException(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
