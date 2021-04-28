package com.team_five.salthub.exception;

import com.team_five.salthub.model.ResponseMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常统一管理
 *
 * @date 2021/04/26
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseMessage exceptionHandler(BaseException e) {
        return ResponseMessage.fail(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseMessage exceptionHandler(Exception e) {
        e.printStackTrace();
        return new ResponseMessage(500, "后端错误: 请联系后端人员处理", null);
    }
}
