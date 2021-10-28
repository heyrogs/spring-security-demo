package com.rog.filter;

import com.rog.common.constant.IConstants;
import com.rog.common.util.HttpUtil;
import com.rog.common.util.JwtLocalUtil;
import com.rog.common.util.ResultUtil;
import com.rog.entity.SysUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * JWT校验过滤器
 *
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    public JwtVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = request.getHeader(IConstants.AUTH_KEY);
        if (!StringUtils.hasText(token)) {
            response.setStatus(IConstants.UNAUTHORIZED);
            HttpUtil.getWrite(response, ResultUtil.UNAUTHORIZED("请先登录"));
            return;
        }
        String newToken = token.replace(IConstants.AUTH_PREFIX, "");
        SysUser loginUser = JwtLocalUtil.parseToken(newToken);
        if (Objects.isNull(loginUser)) {
            response.setStatus(IConstants.UNAUTHORIZED);
            HttpUtil.getWrite(response, ResultUtil.UNAUTHORIZED("token无效"));
            return;
        }
        UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
