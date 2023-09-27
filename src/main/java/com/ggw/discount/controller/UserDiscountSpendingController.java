package com.ggw.discount.controller;

import com.ggw.discount.common.BaseContext;
import com.ggw.discount.common.R;
import com.ggw.discount.entity.UserDiscountSpending;
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

    @PostMapping
    public R<String> add(@RequestBody UserDiscountSpending spending) {
        spending.setUserId(BaseContext.getCurrentId());
        //Long userId = 1704119254814224412L;
        //spending.setUserId(userId);
        userDiscountSpendingService.addWithbalance(spending);
        return R.success("Added successfully");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        userDiscountSpendingService.deleteWithbalance(id);
        return R.success("Deleted successfully");
    }
}
