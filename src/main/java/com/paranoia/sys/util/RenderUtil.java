package com.paranoia.sys.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/22 14:31
 */
public class RenderUtil {

    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
