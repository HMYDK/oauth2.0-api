package com.paranoia.oauth.response;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/14 15:01
 */
public enum OauthEnum {

    ACCESS_FAIL(10015,"公众号未授权第三方平台，请检查授权状态"),
    REDIRECR_IS_NULL(10011,"redirect_uri不能为空"),
    APPID_IS_NULL(10012,"appid不能为空"),
    REDIRECR_NOT_REGIST(10003,"redirect_uri域名与后台配置不一致"),
    SECRET_IS_NULL(10013,"secret不能为空"),
    SECRET_IS_WRONG(10014,"secret错误"),
    GRANT_TYPE_IS_WRONG(10016,"grant_type错误"),
    CODE_LOSE_EFFICACY(10017,"code失效"),
    CODE_IS_WRONG(10018,"code错误"),

    THIRD_TOKEN_LOSE_EFFICACY(11001,"token失效，请通知客户端重新申请授权"),
    THIRD_APP_NAME_IS_NULL(11002,"appName不能为空"),
    THIRD_STATUS_FAIL(11003,"资源账户不存在或被禁用"),
    THIRD_DONOT_HAS_AUTHORITY(11004,"该客户端没有任何API调用权限"),
    THIRD_DONOT_HAS_THIS_AUTHORITY(11005,"该客户端没有该API调用权限"),

    ;
    //-------------------------------------------------------------

    OauthEnum(int key, String value) {
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
