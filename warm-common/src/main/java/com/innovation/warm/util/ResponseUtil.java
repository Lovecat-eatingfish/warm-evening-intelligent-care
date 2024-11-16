package com.innovation.warm.util;

import cn.hutool.json.JSONUtil;
import com.innovation.warm.enumeration.ResultCodeEnum;
import com.innovation.warm.response.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * ClassName: ResponseUtil
 * PackageName: com.innovation.warm.util
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/13 下午10:08
 * @Version: 1.0
 */
@Slf4j
public class ResponseUtil {
    public static void writeErrMessage(HttpServletResponse response, ResultCodeEnum resultCode) {
        response.setStatus(resultCode.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = response.getWriter()) {
            String jsonResponse = JSONUtil.toJsonStr(Result.fail(resultCode));
            writer.print(jsonResponse);
            writer.flush(); // 确保将响应内容写入到输出流
        } catch (IOException e) {
            log.error("响应异常处理失败", e);
        }
    }
}
