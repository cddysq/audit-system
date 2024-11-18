package com.cddysq.auditsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cddysq.auditsystem.dao.User;
import com.cddysq.auditsystem.eunms.RoleEnum;
import com.cddysq.auditsystem.mapper.UserMapper;
import com.cddysq.auditsystem.service.UserService;
import com.cddysq.auditsystem.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 17:00
 * @since 1.0.0
 **/
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public UserVo login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("password", password);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return new UserVo(user.getUserId(), user.getUsername(), RoleEnum.getRole(user.getRole()).getDesc());
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String register(User user) {
        if (findByUsername(user.getUsername()) != null) {
            return "该用户名已被占用，请选择其他用户名！";
        }
        Long userNumber = userMapper.countUserNumber();
        if (userNumber < 1) {
            user.setRole(RoleEnum.SUPER_ADMINISTRATOR.getCode());
        }
        // 保存新用户
        userMapper.insert(user);
        return null;
    }

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }
}
