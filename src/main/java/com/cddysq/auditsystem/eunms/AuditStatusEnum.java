package com.cddysq.auditsystem.eunms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核状态枚举
 *
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2022/8/18 14:44
 * @since 1.0.0
 **/
@Getter
@AllArgsConstructor
public enum AuditStatusEnum {

    /**
     * 待审核
     */
    WAIT(2, "已上传,审核中", ""),

    /**
     * 审核通过
     */
    SUCCESS(1, "审核通过", "green"),

    /**
     * 审核不通过
     */
    FAIL(3, "审核不通过", "red"),

    /**
     * 未知
     */
    NOT_UPLOAD(0, "未知", ""),
    ;

    /**
     * code
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 显示颜色
     */
    private final String color;

    public static AuditStatusEnum getAuditStatus(int code) {
        for (AuditStatusEnum auditStatusEnum : AuditStatusEnum.values()) {
            if (code == auditStatusEnum.getCode()) {
                return auditStatusEnum;
            }
        }
        return NOT_UPLOAD;
    }
}
