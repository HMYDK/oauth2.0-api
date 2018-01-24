package com.paranoia.oauth.dao;

import com.paranoia.oauth.domain.OauthThird;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/18 12:19
 */
public interface OauthThirdMapper extends JpaRepository<OauthThird, Long> {

    OauthThird findByLogogramAndStatus(String name, Integer status);
    OauthThird findByUidAndStatus(String uid, Integer status);
}
