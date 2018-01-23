package com.paranoia.jwt.filter;

import com.paranoia.jwt.doman.TokenDTO;
import com.paranoia.jwt.exception.JwtException;
import com.paranoia.jwt.response.JwtEnum;
import com.paranoia.jwt.service.OauthThirdApiService;
import com.paranoia.jwt.util.JwtUtil;
import com.paranoia.sys.response.Response;
import com.paranoia.sys.util.RenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/22 14:13
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    OauthThirdApiService oauthThirdApiService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String requestUrl = httpServletRequest.getServletPath();
        String result = requestUrl.substring(0, requestUrl.lastIndexOf("/"));

        //过滤指定的路由
        if (httpServletRequest.getServletPath().contains("/user/login")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        final String requestHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            //验证token是否过期,包含了验证jwt是否正确
            try {
                TokenDTO tokenDTO = JwtUtil.unSign(token, TokenDTO.class);
                List<String> methodsList = oauthThirdApiService.findByIdAndStatus(tokenDTO.getAppId(), 0);
                if (ObjectUtils.isEmpty(tokenDTO)) {
                    RenderUtil.renderJson(httpServletResponse, Response.error(JwtEnum.TOKEN_ERROR));
                    return;
                }else if (methodsList == null){
                    RenderUtil.renderJson(httpServletResponse, Response.error(JwtEnum.TEAM_LOCKED));
                    return;
                } else if (methodsList.lastIndexOf(result) <= 0) {
                    RenderUtil.renderJson(httpServletResponse, Response.error(JwtEnum.AUTH_REQUEST_ERROR));
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
