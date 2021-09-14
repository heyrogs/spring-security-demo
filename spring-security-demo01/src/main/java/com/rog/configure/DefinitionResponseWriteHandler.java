package com.rog.configure;

import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 定义返回格式
 *
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/9/14
 **/
public class DefinitionResponseWriteHandler {

    protected final <T> void write(T data, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(JSONUtil.toJsonStr(data));
        }
    }

}
