package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.entity.Store;
import com.ggw.discount.mapper.StoreMapper;
import com.ggw.discount.service.StoreService;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
}
