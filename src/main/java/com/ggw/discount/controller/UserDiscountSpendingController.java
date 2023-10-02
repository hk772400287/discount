package com.ggw.discount.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ggw.discount.common.BaseContext;
import com.ggw.discount.common.R;
import com.ggw.discount.entity.Store;
import com.ggw.discount.entity.UserDiscountSpending;
import com.ggw.discount.service.StoreService;
import com.ggw.discount.service.UserDiscountSpendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/userSpending")
public class UserDiscountSpendingController {

    @Autowired
    private UserDiscountSpendingService userDiscountSpendingService;

    @Autowired
    private StoreService storeService;

    @PostMapping
    public R<String> add(@RequestBody UserDiscountSpending spending) {
        spending.setUserId(BaseContext.getCurrentId());
        Long storeId = spending.getStoreId();
        Store store = storeService.getById(storeId);
        spending.setStoreName(store.getName());
        userDiscountSpendingService.addWithbalance(spending);
        return R.success("Added successfully");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        userDiscountSpendingService.deleteWithbalance(id);
        return R.success("Deleted successfully");
    }
}
