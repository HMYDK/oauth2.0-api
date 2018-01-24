package com.paranoia.oauth.service.impl;

import com.paranoia.oauth.dao.OauthPlugInMapper;
import com.paranoia.oauth.domain.OauthPlugIn;
import com.paranoia.oauth.service.OauthPlugInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/17 16:32
 */
@Service
public class OauthPlugInServiceImpl implements OauthPlugInService{

    @Autowired
    OauthPlugInMapper oauthPlugInMapper;

    @Override
    public OauthPlugIn getByAppId(String appId) {
        return oauthPlugInMapper.findByAppIdAndStatus(appId,0);
    }

    @Override
    public boolean findByAppIdAndAppSecretAndStatus(String appId, String appSecret, int status) {
        if (ObjectUtils.isEmpty(oauthPlugInMapper.findByAppIdAndAppSecretAndStatus(appId,appSecret,status))){
            return false;
        }
        return true;
    }
}
