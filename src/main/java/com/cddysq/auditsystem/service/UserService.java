package com.cddysq.auditsystem.service;

import com.cddysq.auditsystem.dao.User;
import com.cddysq.auditsystem.vo.UserVo;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 16:59
 * @since 1.0.0
 **/
public interface UserService {
    UserVo login(String username, String password);

    /**
     * 账户注册
     *
     * @param user 用户信息
     * @return 提示信息
     */
    String register(User user);

    User findByUsername(String username);
}
