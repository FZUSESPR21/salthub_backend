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
    CANCEL(0L, "cancel"),
    BAN(1L, "ban"),
    TOURIST(2L, "tourist"),
    NORMAL(3L, "normal"),
    ADMINISTRATORS(4L, "administrators");

    public static RoleEnum getRole(Long id) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.getId().equals(id)) {
                return role;
            }
        }
        return RoleEnum.BAN;
    }

    private Long id;
    private String name;
}
