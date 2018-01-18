package com.paranoia.oauth.service.impl;

import com.paranoia.oauth.dao.OauthPlugInDetailsMapper;
import com.paranoia.oauth.domain.OauthPlugInDetails;
import com.paranoia.oauth.service.OauthPlugInDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/18 12:40
 */
@Service
public class OauthPlugInDetailsServiceImpl implements OauthPlugInDetailsService {

    @Autowired
    OauthPlugInDetailsMapper oauthPlugInDetailsMapper;

    @Override
    public List<String> getDetailsByAppIdAndLogogram(String appId, String name, Integer status) {
        List<OauthPlugInDetails> listDO = oauthPlugInDetailsMapper.findByAppIdAndThirdUidAndStatus(appId, name, status);
        if (listDO.isEmpty()) {
            return null;
        }
        List<String> resultList = new ArrayList<>(100);
        listDO.forEach(oauthPlugInDetail -> resultList.add(oauthPlugInDetail.getMethod()));
        return resultList;
    }
}
