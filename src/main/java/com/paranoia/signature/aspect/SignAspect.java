package com.paranoia.signature.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.paranoia.signature.util.MD5Util;
import com.paranoia.sys.response.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/26 14:03
 */
@Aspect
@Component
public class SignAspect {

    @Pointcut("execution(* com.paranoia.signature.controller..*(..))")
    public void signPointCut() {}


    @Around("signPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Object[] args = point.getArgs();
        System.out.println("args = " + this.toString(args));
        if (!validateSign(this.toString(args))) {
            return Response.error("验签失败");
        }
        Object result = point.proceed();
        return result;
    }

    /**
     * 修改 Arrays 中对应方法
     *
     * @param a
     * @return
     */
    private static String toString(Object[] a) {
        if (a == null) {
            return "null";
        }
        int iMax = a.length - 1;
        if (iMax == -1) {
            return "";
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax) {
                return b.toString();
            }
            b.append(", ");
        }
    }

    /**
     * 验签
     *
     * @param jsonString
     * @return
     */
    private boolean validateSign(String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        System.out.println("jsonObject = " + jsonObject);
        Map<String, Object> innerMap = jsonObject.getInnerMap();
        //拿出sign
        String sign = (String) innerMap.get("sign");
        if (StringUtils.isEmpty(sign)) {
            return false;
        }
        innerMap.remove("sign");
        System.out.println("sign = " + sign);
        Collection<String> keySet = innerMap.keySet();
        List<String> list = new ArrayList<>(keySet);
        Collections.sort(list);
        StringBuffer sb = new StringBuffer();
        try {
            for (Object aList : list) {
                sb.append(aList).append("=").append(URLEncoder.encode((String) innerMap.get(aList), "UTF-8")).append("&");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("app_key=10086");
        System.out.println("sb = " + sb);
        String weSign = MD5Util.MD5(sb.toString());
        System.out.println("weSign = " + weSign);
        if (sign.equals(weSign)) {
            return true;
        }
        return false;
    }


}
