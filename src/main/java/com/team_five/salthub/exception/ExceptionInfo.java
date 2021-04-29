package com.team_five.salthub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常信息管理
 *
 * 50: 通用异常
 * 51: 用户异常
 * 52: 博客异常
 * 53: 收藏异常
 * 54: 评论异常
 * 55: 公告异常
 * 56: 举报异常
 *
 * @date 2021/04/26
 */
@AllArgsConstructor
@Getter
public enum ExceptionInfo {
    OK(200, "OK"),

    EMPTYCONTENT(551,"内容为空"),
    EMPTYTITLE(552,"标题为空"),
    AUTHOREMPTY(553,"发布人为空"),
    RECIVEREMPTY(554,"未指定接收人"),
    ILLEGALLENGTH(555,"内容长度过长"),
    ILLEGALTITLELENGTH(556,"标题长度过长"),

    //PASSWORD_ERROR(511, "密码错误");

    MODULE_ID_EMPTY_ERROR(521,"博客模块id为空"),
    MODULE_ID_ERROR(522,"模块id不属于预设模块"),
    TITLE_EMPTY_ERROR(523,"标题为空"),
    TITLE_ERROR(524,"标题过长"),
    CONTENT_EMPTY_ERROR(525,"内容为空"),
    CONTENT_ERROR(526,"内容过长");

    private Integer code;
    private String message;
}
