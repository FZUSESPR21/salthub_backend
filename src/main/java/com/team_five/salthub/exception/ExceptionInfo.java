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

    // 用户异常
    NAME_EMPTY(511, "用户名为空"),
    PASSWORD_EMPTY(512, "密码为空"),
    NAME_NOT_EXIST(513, "用户名不存在"),
    PASSWORD_ERROR(514, "密码错误"),
    MAIL_EMPTY(515, "邮箱为空"),
    NAME_EXIST(516, "用户名已存在"),
    PASSWORD_ILLEGAL(517, "密码非法"),
    VERIFICATION_CODE_INVALID(518, "验证码无效"),
    NAME_ILLEGAL(519, "用户名非法"),
    EMAIL_EXIST(5110, "邮箱已存在"),
    EMAIL_ILLEGAL(5111, "邮箱非法"),
    WAIT(5112, "请休息一下再访问"),
    NICKNAME_ERROR(5113, "昵称过长"),
    SLOGAN_ERROR(5114, "个性签名过长"),

    EMPTY_CONTENT(551, "内容为空"),
    EMPTY_TITLE(552, "标题为空"),
    EMPTY_AUTHOR(553, "发布人为空"),
    EMPTY_ACCOUNT_NAME(554, "用户名为空"),
    ILLEGAL_LENGTH(555, "内容长度过长"),
    ILLEGAL_TITLE_LENGTH(556, "标题长度过长"),
    ACCOUNT_NO_EXIST(557, "该用户名不存在"),
    NOTICE_NO_EXIST(558, "该通知id不存在"),
    EMPTY_BLOG_ID(559, "blog_id为空"),
    EMPTY_COMMENT_ID(5510, "被评论commentId为空"),
    FLAG_ERROR(5511, "flag错误"),
    BLOG_NO_EXIST(5512, "该博客id不存在"),
    FIRST_COMMENT_NO_EXIST(5513, "该一级评论id不存在"),
    SESONDARY_COMMENT_NO_EXIST(5514, "该二级评论id不存在"),


    //PASSWORD_ERROR(511, "密码错误");
    COLLECTION_BLOG_ID_ERROR(531, "该博客不存在"),
    COLLECTION_ACCOUNT_ERROR(531, "该用户不存在"),
    COLLECTION_ALREADY_EXIST_ERROR(532, "您已收藏该文章"),
    COLLECTION_ACCOUNT_EMPTY_ERROR(533, "用户名为空"),
    BLOG_ID_EMPTY_ERROR(534, "博客id为空"),
    COLLECTION_NOT_ERROR(535, "您未收藏该文章"),
    COLLECTION_Account_NOT_ERROR(536, "该收藏用户不存在"),
    TIP_OFF_EXIST_ERROR(561, "该举报记录不存"),
    BAN_ACCOUNT_ERROR(562, "该封禁用户不存在"),
    BAN_NOT_EXIST_ERROR(563, "该用户未被封禁"),
    BAN_EXIST_ERROR(563, "该用户已是封禁状态"),


    MODULE_ID_EMPTY_ERROR(521, "博客模块id为空"),
    MODULE_ID_ERROR(522, "模块id不属于预设模块"),
    TITLE_EMPTY_ERROR(523, "标题为空"),
    TITLE_ERROR(524, "标题过长"),
    CONTENT_EMPTY_ERROR(525, "内容为空"),
    CONTENT_ERROR(526, "内容过长"),
    ACCOUNT_EMPTY_ERROR(527, "用户名为空"),
    TAG_ID_EMPTY_ERROR(528, "标签id为空");

    private Integer code;
    private String message;


}
