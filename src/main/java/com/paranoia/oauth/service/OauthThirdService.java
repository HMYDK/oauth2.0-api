package com.paranoia.oauth.service;

import com.paranoia.oauth.domain.OauthThird;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/18 12:23
 */
public interface OauthThirdService {
    OauthThird getThirdByNameAndStatus(String name,Integer status);
}
