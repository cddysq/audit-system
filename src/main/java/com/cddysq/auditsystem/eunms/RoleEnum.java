package com.cddysq.auditsystem.eunms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/13 3:13
 * @since 1.0.0
 **/
@Getter
@AllArgsConstructor
public enum RoleEnum {
    /**
     * 超管
     */
    SUPER_ADMINISTRATOR(0, "SUPER_ADMINISTRATOR"),
    /**
     * 普通用户
     */
    ORDINARY_USER(1, "ORDINARY_USER"),
    /**
     * 未知用户
     */
    unknown_user(-1, "unknown_user"),
    ;


    /**
     * code
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

    public static RoleEnum getRole(int code) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (code == roleEnum.getCode()) {
                return roleEnum;
            }
        }
        return unknown_user;
    }

}
