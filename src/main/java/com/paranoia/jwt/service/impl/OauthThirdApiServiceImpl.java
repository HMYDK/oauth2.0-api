package com.paranoia.jwt.service.impl;

import com.paranoia.jwt.dao.OauthThirdApiMapper;
import com.paranoia.jwt.doman.OauthThirdApi;
import com.paranoia.jwt.service.OauthThirdApiService;
import com.paranoia.oauth.dao.OauthPlugInDetailsMapper;
import com.paranoia.oauth.domain.OauthPlugInDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/23 10:07
 */
@Service
public class OauthThirdApiServiceImpl implements OauthThirdApiService {

    @Autowired
    OauthThirdApiMapper oauthThirdApiMapper;

    @Autowired
    OauthPlugInDetailsMapper oauthPlugInDetailsMapper;

    @Override
    public List<String> findByIdAndStatus(String id, Integer status) {

        List<OauthPlugInDetails> byAppIdAndStatus = oauthPlugInDetailsMapper.findByAppIdAndStatus(id, status);
        List<String> methods = new ArrayList<>(100);
        if (byAppIdAndStatus != null && byAppIdAndStatus.size() > 0) {
            methods = byAppIdAndStatus.stream()
                                      .map(OauthPlugInDetails::getMethod)
                                      .collect(Collectors.toList());
        }
        List<String> result = new ArrayList<>(100);
        methods.forEach(methodId -> {
            OauthThirdApi oauthThirdApi = oauthThirdApiMapper.findByIdAndStatus(Integer.valueOf(methodId), status);
            if (!ObjectUtils.isEmpty(oauthThirdApi)) {
                result.add(oauthThirdApi.getRoot());
            }
        });
        System.out.println(id + "====result = " + result);
        return result;
    }
}
