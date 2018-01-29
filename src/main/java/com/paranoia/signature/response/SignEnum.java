package com.paranoia.signature.response;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/29 10:40
 */
public enum SignEnum {

    TIEM_IS_NULL(1980, "时间戳为空"),
    SIGN_IS_OUT_TIME(1981, "超出签名有效时间"),
    SIGN_IS_NULL(1982, "签名为空"),
    SIGN_IS_WRONG(1983, "签名违法"),;

    private int key;

    private String value;

    SignEnum() {
    }

    SignEnum(int key, String value) {
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
