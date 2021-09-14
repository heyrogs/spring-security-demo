package com.rog.configure;

import com.rog.common.ResultT;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/9/14
 **/
@Component
public class DefinitionSessionInformationExpiredStrategyHandler extends DefinitionResponseWriteHandler
        implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
            throws IOException {
        write(ResultT.FAIL("您的账号在异地登录，建议修改密码"), event.getResponse());
    }
}
