package com.paranoia.oauth.service.impl;

import com.paranoia.oauth.dao.OauthThirdMapper;
import com.paranoia.oauth.domain.OauthThird;
import com.paranoia.oauth.service.OauthThirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/18 12:24
 */
@Service
public class OauthThirdServiceImpl implements OauthThirdService{

    @Autowired
    OauthThirdMapper oauthThirdMapper;

    @Override
    public OauthThird getThirdByNameAndStatus(String name, Integer status) {
        return oauthThirdMapper.findByLogogramAndStatus(name,status);
    }
}
