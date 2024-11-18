package com.cddysq.auditsystem.vo;

import com.cddysq.auditsystem.eunms.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 16:51
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 角色
     *
     * @see RoleEnum#getCode()
     */
    private String role;
}
