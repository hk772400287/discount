package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.entity.User;
import com.ggw.discount.mapper.UserMapper;
import com.ggw.discount.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
