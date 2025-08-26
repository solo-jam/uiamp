package com.company.uiamp.app.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.uiamp.app.entity.App;
import com.company.uiamp.app.mapper.AppMapper;
import org.springframework.stereotype.Service;

@Service
public class AppService extends ServiceImpl<AppMapper, App> {
}