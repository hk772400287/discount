package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.common.CustomException;
import com.ggw.discount.entity.UserDiscountSpending;
import com.ggw.discount.mapper.UserDiscountSpendingMapper;
import com.ggw.discount.service.UserDiscountBalanceService;
import com.ggw.discount.service.UserDiscountSpendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserDiscountSpendingServiceImpl extends ServiceImpl<UserDiscountSpendingMapper,
        UserDiscountSpending> implements UserDiscountSpendingService {

    @Autowired
    UserDiscountBalanceService userDiscountBalanceService;

    @Override
    public List<UserDiscountSpending> getSpendingListById(Long discountId, Long userId) {
        LambdaQueryWrapper<UserDiscountSpending> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDiscountSpending::getDiscountId, discountId).eq(UserDiscountSpending::getUserId, userId);
        return this.list(wrapper);
    }

    @Transactional
    @Override
    public void addWithbalance(UserDiscountSpending spending) {
        BigDecimal balance = userDiscountBalanceService.getBalanceById(spending.getDiscountId(), spending.getUserId());
        System.out.println(balance);
        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException("Balance is 0, can't add spending");
        }
        BigDecimal amount = spending.getAmount();
        if (amount.compareTo(balance) > 0) {
            spending.setAmount(balance);
            balance = BigDecimal.ZERO;
        } else {
            balance = balance.subtract(amount);
        }
        this.save(spending);
        userDiscountBalanceService.updateBalance(spending.getDiscountId(), spending.getUserId(), balance);
    }

    @Transactional
    @Override
    public void deleteWithbalance(Long id) {
        UserDiscountSpending spending = this.getById(id);
        BigDecimal balance = userDiscountBalanceService.getBalanceById(spending.getDiscountId(), spending.getUserId());
        balance = balance.add(spending.getAmount());
        userDiscountBalanceService.updateBalance(spending.getDiscountId(), spending.getUserId(), balance);
        this.removeById(id);
    }
}
