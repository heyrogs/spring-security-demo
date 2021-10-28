package com.rog.controller;

import com.rog.common.util.JwtLocalUtil;
import com.rog.entity.SysUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("get")
    public SysUser getLoginUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return JwtLocalUtil.parseToken(token.replace("Bear ", ""));
    }

}
