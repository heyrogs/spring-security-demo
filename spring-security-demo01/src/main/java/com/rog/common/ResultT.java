package com.rog.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/9/14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultT<T> implements Serializable {
    private static final long serialVersionUID = -4213759329732428498L;

    private Integer code;

    private String message;

    private T data;

    private static final Integer SUCCESS_CODE = 200;
    private static final Integer FAILURE_CODE = 500;

    public static <T> ResultT SUCCESS(String message, T data) {
        return new ResultT(SUCCESS_CODE, message, data);
    }

    public static <T> ResultT SUCCESS(T data) {
        return SUCCESS("success", data);
    }

    public static ResultT FAIL(String message) {
        return new ResultT(FAILURE_CODE, message, null);
    }

}
