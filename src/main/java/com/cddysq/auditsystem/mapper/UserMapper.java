package com.cddysq.auditsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cddysq.auditsystem.dao.User;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 16:58
 * @since 1.0.0
 **/
public interface UserMapper extends BaseMapper<User> {
    /**
     * 统计用户数量
     *
     * @return 用户数量
     */
    default Long countUserNumber() {
        return this.selectCount(null);
    }
}
