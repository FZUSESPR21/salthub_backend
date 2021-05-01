package com.team_five.salthub.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.team_five.salthub.model.ResponseMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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
        if (e instanceof NotLoginException) {
            return new ResponseMessage(403, "请先登录", null);
        } else if (e instanceof NotRoleException) {
            return new ResponseMessage(403, "您没有权限", null);
        } else if (e instanceof NotPermissionException) {
            return new ResponseMessage(403, "您没有权限", null);
        } else if (e instanceof MaxUploadSizeExceededException) {
            return new ResponseMessage(100, "文件太大", null);
        }
        e.printStackTrace();
        return new ResponseMessage(500, "后端错误", null);
    }
}
