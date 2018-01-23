package com.paranoia.oauth.dao;

import com.paranoia.oauth.domain.OauthPlugInDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/18 11:40
 */
public interface OauthPlugInDetailsMapper extends JpaRepository<OauthPlugInDetails, Long> {

    List<OauthPlugInDetails> findByAppIdAndThirdUidAndStatus(String appId, String thridUid, Integer status);

    List<OauthPlugInDetails> findByAppIdAndStatus(String appId, Integer status);

}
