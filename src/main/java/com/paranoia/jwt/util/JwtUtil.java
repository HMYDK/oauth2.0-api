package com.paranoia.jwt.util;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranoia.jwt.config.JwtConfigByProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/19 15:07
 */
public class JwtUtil {

    private static final String EXP = "exp";

    private static final String PAYLOAD = "payload";


    /**
     * 加密
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String sign(T object ,JwtConfigByProperties jwtConfigByProperties) {
        try {
            final JWTSigner signer = new JWTSigner(jwtConfigByProperties.getSecret());
            final Map<String, Object> claims = new HashMap<String, Object>(16);
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(object);
            claims.put(PAYLOAD, jsonString);
            claims.put(EXP, System.currentTimeMillis() + jwtConfigByProperties.getExpiration());
            return signer.sign(claims);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 解密
     *
     * @param jwt
     * @param classT
     * @param <T>
     * @return
     */
    public static <T> T unSign(String jwt, Class<T> classT ,JwtConfigByProperties jwtConfigByProperties) {
        final JWTVerifier verifier = new JWTVerifier(jwtConfigByProperties.getSecret());
        try {
            final Map<String, Object> claims = verifier.verify(jwt);
            if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
                long exp = (Long) claims.get(EXP);
                long currentTimeMillis = System.currentTimeMillis();
                //判断权限是否超时
                if (exp > currentTimeMillis) {
                    String json = (String) claims.get(PAYLOAD);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(json, classT);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证签名是否超时
     *
     * @param jwt
     * @return
     */
    public static boolean unSign(String jwt,JwtConfigByProperties jwtConfigByProperties) {
        final JWTVerifier verifier = new JWTVerifier(jwtConfigByProperties.getSecret());
        try {
            final Map<String, Object> claims = verifier.verify(jwt);
            if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
                long exp = (Long) claims.get(EXP);
                long currentTimeMillis = System.currentTimeMillis();
                //判断权限是否超时
                if (exp > currentTimeMillis) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
