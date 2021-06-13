package com.team_five.salthub.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 博客状态
 *
 * @date 2021/04/27
 */
@AllArgsConstructor
@Getter
public enum BlogStateEnum {
    DELETE(0L, "删除"),
    BAN(1L, "封禁"),
    NORMAL(2L, "正常"),
    TREEHOLE(3l, "树洞");
    private Long id;
    private String name;
}
