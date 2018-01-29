package com.paranoia.signature.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.paranoia.signature.response.SignEnum;
import com.paranoia.signature.util.MD5Util;
import com.paranoia.sys.response.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(SignAspect.class);

    @Pointcut("execution(* com.paranoia.signature.controller..*(..))")
    public void signPointCut() {}


    @Around("signPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Object[] args = point.getArgs();
        logger.info(String.format("args = %s", this.toString(args)));
        Response response = validateSign(this.toString(args));
        if (0 != (int)response.get("code") ){
            return response;
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
    private Response validateSign(String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        System.out.println("jsonObject = " + jsonObject);
        //校验时间，有效期五分钟
        String clientTime = (String) jsonObject.get("time_stamp");
        if (StringUtils.isEmpty(clientTime)){
            return Response.error(SignEnum.TIEM_IS_NULL);
        }
        Date date = new Date(Long.valueOf(clientTime));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)+5);
        if (System.currentTimeMillis() > calendar.getTimeInMillis()){
            return Response.error(SignEnum.SIGN_IS_OUT_TIME);
        }
        Map<String, Object> innerMap = jsonObject.getInnerMap();
        //校验sign
        String sign = (String) innerMap.get("sign");
        if (StringUtils.isEmpty(sign)) {
            return Response.error(SignEnum.SIGN_IS_NULL);
        }
        innerMap.remove("sign");
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
        String weSign = MD5Util.MD5(sb.toString());
        if (!sign.equals(weSign)) {
            return Response.error(SignEnum.SIGN_IS_WRONG);
        }
        return Response.ok();
    }


}
