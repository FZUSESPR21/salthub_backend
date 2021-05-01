package com.team_five.salthub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常信息管理
 * <p>
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

    EMPTY_CONTENT(551, "内容为空"),
    EMPTY_TITLE(552, "标题为空"),
    EMPTY_AUTHOR(553, "发布人为空"),
    EMPTY_ACCOUNTNAME(554, "用户名为空"),
    ILLEGAL_LENGTH(555, "内容长度过长"),
    ILLEGAL_TITLELENGTH(556, "标题长度过长"),

    //PASSWORD_ERROR(511, "密码错误");
    COLLECTION_BLOG_ID_ERROR(531, "该博客不存在"),
    COLLECTION_ACCOUNT_ERROR(531, "该用户不存在"),
    COLLECTION_ALREADY_EXIST_ERROR(532, "您已收藏该文章"),
    COLLECTION_ACCOUNT_EMPTY_ERROR(533, "用户名为空"),
    BLOD_ID_EMPTY_ERROR(534, "博客id为空"),
    COLLECTION_NOT_ERRO(535, "您未收藏该文章"),

    MODULE_ID_EMPTY_ERROR(521, "博客模块id为空"),
    MODULE_ID_ERROR(522, "模块id不属于预设模块"),
    TITLE_EMPTY_ERROR(523, "标题为空"),
    TITLE_ERROR(524, "标题过长"),
    CONTENT_EMPTY_ERROR(525, "内容为空"),
    CONTENT_ERROR(526, "内容过长"),
    ACCOUNT_EMPTY_ERROR(526, "用户名为空"),
    TAG_ID_EMPTY_ERROR(527, "标签id为空");

    private Integer code;
    private String message;


}
