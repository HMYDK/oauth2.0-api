package com.paranoia.jwt.dao;

import com.paranoia.jwt.doman.OauthThirdApi;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/23 9:58
 */
public interface OauthThirdApiMapper extends JpaRepository<OauthThirdApi,Long> {

    OauthThirdApi findByIdAndStatus(Integer id, Integer status);
}
