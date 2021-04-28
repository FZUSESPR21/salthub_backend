package com.team_five.salthub.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标签枚举
 *
 * @date 2021/04/27
 */
@AllArgsConstructor
@Getter
public enum TagEnum {
    FZU(0L, "福州大学"),
    EXPERIENCE(1L, "经验"),
    INFORMATION(2L, "资讯");

    private Long id;
    private String name;
}
