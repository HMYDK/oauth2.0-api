package com.paranoia.jwt.exception;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/22 14:35
 */
public class JwtException extends RuntimeException {

    private static final long serialVersionUID = 6030284046582374012L;

    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
