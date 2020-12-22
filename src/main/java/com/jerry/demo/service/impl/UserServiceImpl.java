package com.jerry.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.demo.domain.User;
import com.jerry.demo.mapper.UserMapper;
import com.jerry.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: Jerry
 * @create: 2020-12-22 13:25
 * @description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
