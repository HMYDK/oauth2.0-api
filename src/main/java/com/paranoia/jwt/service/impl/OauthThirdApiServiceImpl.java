package com.paranoia.jwt.service.impl;

import com.paranoia.jwt.dao.OauthThirdApiMapper;
import com.paranoia.jwt.doman.OauthThirdApi;
import com.paranoia.jwt.service.OauthThirdApiService;
import com.paranoia.oauth.dao.OauthPlugInDetailsMapper;
import com.paranoia.oauth.dao.OauthPlugInMapper;
import com.paranoia.oauth.dao.OauthThirdMapper;
import com.paranoia.oauth.domain.OauthPlugIn;
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

    @Autowired
    OauthPlugInMapper oauthPlugInMapper;

    @Autowired
    OauthThirdMapper oauthThirdMapper;

    @Override
    public List<String> findByIdAndStatus(String id, Integer status) {
        //校验团队权限
        OauthPlugIn oauthPlugIn =  oauthPlugInMapper.findByAppIdAndStatus(id,status);
        if (ObjectUtils.isEmpty(oauthPlugIn)){
            return null;
        }
        //校验团队的api列表权限
        List<OauthPlugInDetails> byAppIdAndStatus = oauthPlugInDetailsMapper.findByAppIdAndStatus(id, status);
        List<String> methods = new ArrayList<>(100);
        if (byAppIdAndStatus != null && byAppIdAndStatus.size() > 0) {
            //因为数据关联的原因，校验第三方权限只能放在这个位置，虽不合理，只能为之
            OauthPlugInDetails oauthPlugInDetails = byAppIdAndStatus.get(0);
            String thirdUid = oauthPlugInDetails.getThirdUid();
            if (ObjectUtils.isEmpty(oauthThirdMapper.findByUidAndStatus(thirdUid,0))){
                return null;
            }
            methods = byAppIdAndStatus.stream()
                                      .map(OauthPlugInDetails::getMethod)
                                      .collect(Collectors.toList());
        }
        //校验第三方的API是否有权限
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
