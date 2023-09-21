package com.ggw.discount.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggw.discount.entity.UserDiscountSpending;

import java.util.List;

public interface UserDiscountSpendingService extends IService<UserDiscountSpending> {
    List<UserDiscountSpending> getSpendingListById(Long discountId, Long userId);

    void addWithbalance(UserDiscountSpending spending);

    void deleteWithbalance(Long id);
}
