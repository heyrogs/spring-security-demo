package com.rog.config;

import com.rog.filter.JwtLoginFilter;
import com.rog.filter.JwtVerifyFilter;
import com.rog.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
@Configuration
@RequiredArgsConstructor
public class LocalSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ISysUserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 设置认证service和密码配置方式
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    /**
     * 设置接口权限
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(new JwtVerifyFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
