package com.team_five.salthub.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份枚举
 *
 * @date 2021/04/27
 */
@AllArgsConstructor
@Getter
public enum RoleEnum {
    cancel(0L, "注销用户"),
    BAN(1L, "封禁用户"),
    tourist(2L, "游客用户"),
    NORMAL(3L, "普通用户"),
    ADMINISTRATORS(4L, "管理员用户");

    private Long id;
    private String name;
}
