package com.paranoia.jwt.filter;

import com.paranoia.jwt.exception.JwtException;
import com.paranoia.jwt.response.JwtEnum;
import com.paranoia.jwt.util.JwtUtil;
import com.paranoia.sys.response.Response;
import com.paranoia.sys.util.RenderUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/22 14:13
 */
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //过滤指定的路由
        if (!httpServletRequest.getServletPath().contains("/user")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        final String requestHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = JwtUtil.unSign(token);
                if (!flag) {
                    RenderUtil.renderJson(httpServletResponse, Response.error(JwtEnum.TOKEN_EXPIRED));
                    return;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败了
                RenderUtil.renderJson(httpServletResponse, Response.error(JwtEnum.TOKEN_ERROR));
                return;
            }
        } else {
            //header没有带Bearer字段
            RenderUtil.renderJson(httpServletResponse, Response.error(JwtEnum.TOKEN_ERROR));
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
