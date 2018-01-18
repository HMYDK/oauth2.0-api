package com.paranoia.oauth.service;

import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/18 12:38
 */
public interface OauthPlugInDetailsService {
    List<String> getDetailsByAppIdAndLogogram(String appId, String name, Integer status);
}
