package com.rog.common.util;

import com.rog.entity.ResultT;

import static com.rog.common.constant.IConstants.SUCCESS;
import static com.rog.common.constant.IConstants.FAILURE;
import static com.rog.common.constant.IConstants.UNAUTHORIZED;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
public class ResultUtil {

    public static <T> ResultT<T> SUCCESS(String message, T data) {
        return new ResultT<>(SUCCESS, message, data);
    }

    public static <T> ResultT<T> SUCCESS(T data) {
        return SUCCESS("success", data);
    }

    public static ResultT FAILURE(String message) {
        return new ResultT<>(FAILURE, message, null);
    }

    public static ResultT UNAUTHORIZED(String message) {
        return new ResultT(UNAUTHORIZED, message, null);
    }
}
