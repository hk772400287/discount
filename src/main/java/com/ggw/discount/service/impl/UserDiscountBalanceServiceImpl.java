package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.entity.UserDiscountBalance;
import com.ggw.discount.mapper.UserDiscountBalanceMapper;
import com.ggw.discount.service.UserDiscountBalanceService;
import org.springframework.stereotype.Service;

@Service
public class UserDiscountBalanceServiceImpl extends ServiceImpl<UserDiscountBalanceMapper,
        UserDiscountBalance> implements UserDiscountBalanceService {
}
