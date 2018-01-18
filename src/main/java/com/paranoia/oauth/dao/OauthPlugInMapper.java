package com.paranoia.oauth.dao;

import com.paranoia.oauth.domain.OauthPlugIn;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/17 16:30
 */
public interface OauthPlugInMapper extends JpaRepository<OauthPlugIn,Long>{
    OauthPlugIn findByAppIdAndStatus(String appId,int status);
}
