package com.paranoia.signature.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/25 17:15
 */
public class MD5Util {

    public static void main(String[] args) {
        String ss = MD5("这his亚IE吧v");
        System.out.println("ss = " + ss);
        //76D80224611FC919A5D54F0FF9FBA446
        //76D80224611FC919A5D54F0FF9FBA446
    }

    public final static String MD5(String pwd) {
        //用于加密的字符
        char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = pwd.getBytes();

            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            //返回经过加密后的字符串
            return Hex.encodeHexString(md).toUpperCase();

        } catch (Exception e) {
            return null;
        }
    }
}
