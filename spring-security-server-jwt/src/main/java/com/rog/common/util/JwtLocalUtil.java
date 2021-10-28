package com.rog.common.util;

import cn.hutool.jwt.JWT;
import com.rog.entity.SysRole;
import com.rog.entity.SysUser;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
public class JwtLocalUtil {

    // 密钥
    static final byte[] KEY = "TEST".getBytes(StandardCharsets.UTF_8);

    /**
     * 根据用户对象生成TOKEN
     */
    public static String createToken(SysUser user) {
        return JWT.create()
                .setPayload("username", user.getUsername())
                .setPayload("authorities", user.getAuthorities())
                .setKey(KEY)
                .sign();
    }

    /**
     * 把token解析为用户信息
     */
    public static SysUser parseToken(String token) {
        SysUser user = new SysUser();
        JWT jwt = JWT.of(token).setKey(KEY);
        user.setUsername((String) jwt.getPayload("username"));
        user.setAuthorities((Collection<SysRole>) jwt.getPayload("authorities"));
        return user;
    }

    /**
     * token有效检测
     */
    public static Boolean verify(String token) {
        return JWT.of(token).setKey(KEY).verify();
    }

}
