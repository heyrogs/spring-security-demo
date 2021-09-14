package com.rog.configure;

import com.rog.serivce.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/9/14
 **/
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    private final DefinitionSessionInformationExpiredStrategyHandler sessionInformationExpiredStrategyHandler;
    private final DefinitionAuthenticationEntryPointHandler authenticationEntryPointHandler;
    private final DefinitionAuthenticationFailureHandler authenticationFailureHandler;
    private final DefinitionAuthenticationLogoutHandler authenticationLogoutHandler;
    private final DefinitionAuthenticationSuccessHandler authenticationSuccessHandler;
    private final DefinitionAccessDenyHandler accessDenyHandler;

    /**
     * 引用自定义用户数据查寻
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }

    /**
     * 用户密码加密方式
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new DefinitionAuthenticationProvider(userDetailsService(), bCryptPasswordEncoder());
    }


    /**
     * 认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * 授权
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 解决跨域问题，csrf会与 restful 风格冲突，默认时开启的所以需要关闭
        http.cors().and().csrf().disable();

        // 授权配置
        http.authorizeRequests()
                .anyRequest().authenticated()
                // 退出成功处理
                .and()
                .logout().permitAll().logoutSuccessHandler(authenticationLogoutHandler)
                .deleteCookies("JSESSIONID")
                // 登录
                .and()
                .formLogin().permitAll()
                .successHandler(authenticationSuccessHandler) // 登录成功逻辑处理
                .failureHandler(authenticationFailureHandler) // 登录失败逻辑处理
                // 异常问题处理
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDenyHandler)   // 权限不足的逻辑处理
                .authenticationEntryPoint(authenticationEntryPointHandler) // 未登录时的逻辑处理
                // 多账号登录问题
                .and()
                .sessionManagement()
                .maximumSessions(1) // 最多只能一个用户登录一个账号
                .expiredSessionStrategy(sessionInformationExpiredStrategyHandler) // 异地登录逻辑处理
        ;

    }
}
