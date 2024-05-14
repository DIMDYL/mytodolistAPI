package org.example.api.handler;

import org.example.api.exception.BaseException;
import org.example.api.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class HandlerException {
    /**
     * 处理自定义异常
     * @param exception
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result handlerexception(BaseException exception){
        // 向前端发送异常信息
        return Result.error(exception.getMessage());
    }

    /**
     * 用户名重复异常
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result sqlIntegrityConstraintViolationException(){
        return Result.error("用户名已经存在了");
    }
}
