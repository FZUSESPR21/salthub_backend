package com.team_five.salthub.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 板块枚举
 *
 * @date 2021/04/27
 */
@AllArgsConstructor
@Getter
public enum ModuleEnum {
    FZU(0L, "福州大学"),
    OTHER_SCHOOL(1L, "外校"),
    MISCELLANY(2L, "杂谈"),
    GROUP_COURSE(3L, "拼课");

    private Long id;
    private String name;
}
