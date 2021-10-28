package com.rog.filter;

import com.alibaba.fastjson.JSONObject;
import com.rog.common.constant.IConstants;
import com.rog.common.util.HttpUtil;
import com.rog.common.util.JwtLocalUtil;
import com.rog.common.util.ResultUtil;
import com.rog.entity.SysRole;
import com.rog.entity.SysUser;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            SysUser loginUser = JSONObject.parseObject(request.getInputStream(), SysUser.class);
            username = loginUser.getUsername();
            password = loginUser.getPassword();
        }
        try {
            return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            response.setStatus(IConstants.UNAUTHORIZED);
            HttpUtil.getWrite(response, ResultUtil.FAILURE("账号或密码错误"));
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {
        SysUser loginUser = new SysUser();
        loginUser.setUsername(authResult.getName());
        loginUser.setAuthorities((Collection<SysRole>) authResult.getAuthorities());
        String token = JwtLocalUtil.createToken(loginUser);
        response.setHeader(IConstants.AUTH_KEY, IConstants.AUTH_PREFIX + token);
        HttpUtil.getWrite(response, ResultUtil.SUCCESS(token));
    }

}
