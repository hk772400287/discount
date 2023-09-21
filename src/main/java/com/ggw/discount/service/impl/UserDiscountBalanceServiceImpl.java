package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.entity.UserDiscountBalance;
import com.ggw.discount.mapper.UserDiscountBalanceMapper;
import com.ggw.discount.service.UserDiscountBalanceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserDiscountBalanceServiceImpl extends ServiceImpl<UserDiscountBalanceMapper,
        UserDiscountBalance> implements UserDiscountBalanceService {

    @Override
    public BigDecimal getBalanceById(Long discountId, Long userId) {
        LambdaQueryWrapper<UserDiscountBalance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDiscountBalance::getUserId, userId).eq(UserDiscountBalance::getDiscountId, discountId);
        UserDiscountBalance userDiscountBalance = this.getOne(wrapper);
        return userDiscountBalance.getBalanceAmount();
    }

    @Override
    public void updateBalance(Long discountId, Long userId, BigDecimal balance) {
        LambdaUpdateWrapper<UserDiscountBalance> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserDiscountBalance::getUserId, userId).eq(UserDiscountBalance::getDiscountId, discountId).
                set(UserDiscountBalance::getBalanceAmount, balance);
        this.update(wrapper);
    }
}
