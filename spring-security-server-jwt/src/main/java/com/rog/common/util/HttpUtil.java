package com.rog.common.util;

import com.alibaba.fastjson.JSONObject;
import com.rog.entity.ResultT;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
public class HttpUtil {

    /**
     * 将信息响应回去
     */
    public static void getWrite(HttpServletResponse response, ResultT data) {
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(JSONObject.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
