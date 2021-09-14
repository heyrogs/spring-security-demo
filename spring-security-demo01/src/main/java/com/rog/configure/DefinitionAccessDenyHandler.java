package com.rog.configure;

import com.rog.common.ResultT;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权限访问逻辑处理
 *
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/9/14
 **/
@Component
public class DefinitionAccessDenyHandler extends DefinitionResponseWriteHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        write(ResultT.FAIL("无权限访问"), response);
    }
}
