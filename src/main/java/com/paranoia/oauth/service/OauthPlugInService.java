package com.paranoia.oauth.service;

import com.paranoia.oauth.domain.OauthPlugIn;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/17 16:31
 */
public interface OauthPlugInService {
    OauthPlugIn getByAppId(String appId);

    boolean findByAppIdAndAppSecretAndStatus(String appId,String appSecret,int status);
}
