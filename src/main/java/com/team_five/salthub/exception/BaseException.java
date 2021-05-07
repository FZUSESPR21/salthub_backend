package com.team_five.salthub.exception;

import lombok.Getter;

/**
 * 基本异常类，进行统一异常管理
 *
 * @date 2021/04/26
 */
@Getter
public class BaseException extends RuntimeException {
    private Integer code;

    public BaseException(ExceptionInfo exceptionInfo) {
        super(exceptionInfo.getMessage());
        code = exceptionInfo.getCode();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
