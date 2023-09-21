package com.ggw.discount.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggw.discount.entity.UserDiscountBalance;

import java.math.BigDecimal;

public interface UserDiscountBalanceService extends IService<UserDiscountBalance> {

    BigDecimal getBalanceById(Long discountId, Long userId);

    void updateBalance(Long discountId, Long userId, BigDecimal balance);
}
