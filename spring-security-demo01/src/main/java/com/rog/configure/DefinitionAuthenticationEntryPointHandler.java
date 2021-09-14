package com.rog.configure;

import com.rog.common.ResultT;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义未登录的处理逻辑
 *
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/9/14
 **/
@Component
public class DefinitionAuthenticationEntryPointHandler extends DefinitionResponseWriteHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException e)
            throws IOException {
        write(ResultT.FAIL("需要登录"), response);
    }
}
