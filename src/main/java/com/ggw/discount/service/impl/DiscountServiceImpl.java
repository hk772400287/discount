package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.entity.Discount;
import com.ggw.discount.mapper.DiscountMapper;
import com.ggw.discount.service.DiscountService;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl extends ServiceImpl<DiscountMapper, Discount> implements DiscountService {
}
