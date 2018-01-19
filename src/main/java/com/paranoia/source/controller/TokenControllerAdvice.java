package com.paranoia.source.controller;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/17 18:58
 */
//@ControllerAdvice(basePackages = "com.paranoia.modules.designercase.controller")
public class TokenControllerAdvice {

    Logger logger = LoggerFactory.getLogger(TokenControllerAdvice.class);

    @ModelAttribute
    public void getBobyInfo(HttpEntity<String> httpEntity, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, ValidateException {
        //获取参数
        String data = httpEntity.getBody();
        ObjectMapper objectMapper =  new ObjectMapper();
        Param paramBean = data != null ? objectMapper.readValue(data, Param.class) : null;
        if (paramBean != null)
        {
            String name = paramBean.getName();
            String token = paramBean.getToken();
            if(StringUtils.isBlank(name)){
                throw new ValidateException( 123, "token不能为空");
            }else if(validate(token)){
                throw new ValidateException( 456, "无效的token");
            }
        }
    }

    private boolean validate(String token) {
        return false;
    }

    //捕获ValidateException的异常，统一返回
    @ExceptionHandler({ValidateException.class})
    @ResponseBody
    public ResultBean validateException(ValidateException ex){
        ResultBean result = new ResultBean(ex.getKey(),ex.getValue());
        return result;
    }

}
