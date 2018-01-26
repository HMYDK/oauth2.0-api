package com.paranoia;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/23 10:29
 */
public class StringTest {
    public static void main(String[] args) {
        String ss = "/user/login";

        String result = ss.substring(0,ss.lastIndexOf("/"));
        System.out.println("result = " + result);
    }
}
