package com.paranoia.jwt.response;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/14 15:01
 */
public enum JwtEnum {

    /**
     * token异常
     */
    TOKEN_EXPIRED(10086, "token过期"),
    TOKEN_ERROR(10010, "token验证失败"),

    /**
     * 签名异常
     */
    SIGN_ERROR(12315, "签名验证失败"),

    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(14540, "账号密码错误");

    //-------------------------------------------------------------

    JwtEnum(int key, String value) {
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
