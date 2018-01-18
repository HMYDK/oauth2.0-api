package com.paranoia.modules.designercase.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.util.StringUtils;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.List;
import java.util.Map;

/**
 * Json util class based on fastjson
 * 
 */
public class FastJsonUtil {
	private static final SerializeConfig config;
	  
    static {  
        config = new SerializeConfig();
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
    }  
  
    private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
    };  
    
    public static String toJSONStringWithoutNull(Object object) {  
        return JSON.toJSONString(object, config);
    } 
    
    public static String toJSONString(Object object) {  
        return JSON.toJSONString(object, config, features);
    }  
      
    public static String toJSONNoFeatures(Object object) {  
        return JSON.toJSONString(object, config);
    }  
  
    public static Object toBean(String text) {  
        if(StringUtils.isEmpty(text)){
            return null;
        }
        return JSON.parse(text);
    }  
  
    public static <T> T toBean(String text, Class<T> clazz) {  
        if(StringUtils.isEmpty(text)){
            return null;
        }
        return JSON.parseObject(text, clazz);
    }
  
    /**
     * 转换为数组
     * @param text
     * @param <T>
     * @return
     */
    public static <T> Object[] toArray(String text) {  
        return toArray(text, null);  
    }  
  
    
    /**
     * 转换为数组
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Object[] toArray(String text, Class<T> clazz) {  
        return JSON.parseArray(text, clazz).toArray();
    }  
  
    /**
     * 转换为List
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String text, Class<T> clazz) {  
        return JSON.parseArray(text, clazz);
    }  
  
    /**  
     * 将javabean转化为序列化的json字符串  
     * @param keyvalue  
     * @return  
     */  
    public static Object beanToJson(KeyValue keyvalue) {  
        String textJson = JSON.toJSONString(keyvalue);
        Object objectJson  = JSON.parse(textJson);
        return objectJson;  
    }  
      
    /**  
     * 将string转化为序列化的json字符串  
     * @param text
     * @return  
     */  
    public static Object textToJson(String text) {  
        Object objectJson  = JSON.parse(text);
        return objectJson;  
    }
      
    /**  
     * json字符串转化为map  
     * @param s  
     * @return  
     */  
    @SuppressWarnings("rawtypes")
	public static Map stringToCollect(String s) {  
        Map m = JSONObject.parseObject(s);
        return m;  
    }  
      
    /**  
     * 将map转化为string  
     * @param m  
     * @return  
     */  
    @SuppressWarnings("rawtypes")
	public static String collectToString(Map m) {  
        String s = JSONObject.toJSONString(m);
        return s;  
	}

    /**
     * jsonArray转换成List
     * @param jsonArray
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonArrayToList(JSONArray jsonArray, Class<T> clazz) {
        String json = JSON.toJSONString(jsonArray);
        return JSON.parseArray(json, clazz);
    }

    /**
     * 生产简单jsonObject
     * @param key
     * @param object
     * @return
     */

    public static JSONObject putParmToJsonObject(String key, Object object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, object);
        return jsonObject;
    }
    

}
