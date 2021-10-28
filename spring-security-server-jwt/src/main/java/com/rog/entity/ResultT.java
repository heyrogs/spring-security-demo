package com.rog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 接口返回模板格式
 *
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultT<T> implements Serializable {
    private static final long serialVersionUID = -7459883610222339346L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

}
