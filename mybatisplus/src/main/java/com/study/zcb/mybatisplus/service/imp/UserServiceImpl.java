package com.study.zcb.mybatisplus.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.zcb.mybatisplus.entity.User;
import com.study.zcb.mybatisplus.mapper.UserMapper;
import com.study.zcb.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService {
}
