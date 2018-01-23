package com.paranoia.jwt.service;

import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/23 10:06
 */
public interface OauthThirdApiService {

    List<String> findByIdAndStatus(String id, Integer status);

}
