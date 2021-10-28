package com.rog.common.constant;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
public interface IConstants {

    /**
     * 请求成功状态码
     */
    Integer SUCCESS = 200;
    /**
     * 请求失败状态码
     */
    Integer FAILURE = HttpServletResponse.SC_BAD_REQUEST;
    /**
     * 无操作权限
     */
    Integer UNAUTHORIZED = HttpServletResponse.SC_UNAUTHORIZED;

    /**
     * 请求授权前缀
     */
    String AUTH_PREFIX = "Bear ";
    /**
     * 请求授权时带的KEY
     */
    String AUTH_KEY = "Authorization";

}
