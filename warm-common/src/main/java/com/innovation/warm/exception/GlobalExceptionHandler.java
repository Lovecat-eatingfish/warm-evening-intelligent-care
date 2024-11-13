package com.innovation.warm.exception;


import com.innovation.warm.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: GlobalExceptionHandler
 * PackageName: com.enquan.lease.common.exception
 * Description:
 *
 * @Author: 32782
 * @Date: 10/8/2024 上午8:48
 * @Version: 1.0
 */
//@RestControllerAdvice == ResponseBody + ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public Result handler(ServiceException e){
        log.error(e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    //这个异常类是在controller发生异常时候用的
    @ExceptionHandler(Exception.class)
    public Result handler(Exception e) {
        log.error(e.getMessage());
        return Result.fail(500, e.getMessage());
    }

}
