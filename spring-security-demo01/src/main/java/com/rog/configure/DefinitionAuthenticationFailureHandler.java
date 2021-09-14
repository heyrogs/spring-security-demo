package com.rog.configure;

import com.rog.common.ResultT;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理
 *
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/9/14
 **/
@Component
public class DefinitionAuthenticationFailureHandler extends DefinitionResponseWriteHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException {
        write(ResultT.FAIL(e.getMessage()), response);
    }
}
