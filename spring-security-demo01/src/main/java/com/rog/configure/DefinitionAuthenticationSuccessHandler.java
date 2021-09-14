package com.rog.configure;

import com.rog.common.ResultT;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/9/14
 **/
@Component
public class DefinitionAuthenticationSuccessHandler extends DefinitionResponseWriteHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        write(ResultT.SUCCESS("登录成功", authentication), response);
    }
}
