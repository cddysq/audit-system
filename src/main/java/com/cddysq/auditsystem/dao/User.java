package com.cddysq.auditsystem.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cddysq.auditsystem.eunms.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 16:51
 * @since 1.0.0
 **/
@Data
@TableName("user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 账号密码
     */
    private String password;
    /**
     * 角色
     *
     * @see RoleEnum#getCode()
     */
    private Integer role;
    /**
     * 创建时间
     */
    private Date createTime;
}
