package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.entity.DiscountStore;
import com.ggw.discount.mapper.DiscountStoreMapper;
import com.ggw.discount.service.DiscountStoreService;
import org.springframework.stereotype.Service;

@Service
public class DiscountStoreServiceImpl extends ServiceImpl<DiscountStoreMapper, DiscountStore> implements DiscountStoreService {
}
