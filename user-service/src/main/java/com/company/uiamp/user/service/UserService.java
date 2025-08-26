package com.company.uiamp.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.uiamp.user.entity.User;
import com.company.uiamp.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}