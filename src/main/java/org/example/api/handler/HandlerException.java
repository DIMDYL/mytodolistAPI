package org.example.api.handler;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.example.api.content.FileMsg;
import org.example.api.exception.BaseException;
import org.example.api.result.Result;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 验证码发送失败
     * @param e
     * @return
     */
    @ExceptionHandler(MailException.class)
    public Result SendMailException(MailException e){
        return Result.error(e.getMessage());
    }

    /**
     * 图片文件超出限制大小
     * @param e
     * @return
     */
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public Result FileSizeLimitExceedException(FileSizeLimitExceededException e){
        return Result.error(FileMsg.FILE_SIZE_LIMIT_EXCEED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result MethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> errorList = new ArrayList<>();
//      ①获取错误信息表
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().stream().forEach((a)->{
            String parameter = a.toString().split(":")[0].split("field")[1];
            errorList.add(parameter.substring(2, parameter.length() - 1) + ":" + a.getDefaultMessage());
        });

        return Result.error(errorList.toString());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result ConstraintViolationException(ConstraintViolationException e){
        List<String> errorList = new ArrayList<>();
        e.getConstraintViolations().stream().forEach((a)->{
            String[] split = a.getPropertyPath().toString().split("\\.");
            errorList.add(split[1]+ ":" + a.getMessage());
        });

        return Result.error(errorList.toString());
    }
}
