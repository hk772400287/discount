package com.ggw.discount.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ggw.discount.common.R;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.DiscountStore;
import com.ggw.discount.entity.Store;
import com.ggw.discount.service.DiscountService;
import com.ggw.discount.service.DiscountStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountStoreService discountStoreService;

    @GetMapping("/forAdminByPage/{page}/{pageSize}")
    public R<Page> getAllForAdmin(@PathVariable int page, @PathVariable int pageSize, Store store) {
        Page<Discount> discountPage = new Page<>(page, pageSize);
        Page<DiscountDto> discountDtoPage = discountService.getAllWithStoresBySpecifyStoreNameByPage(store, discountPage);
        return R.success(discountDtoPage);
    }

    @PostMapping
    public R<String> add(@RequestBody DiscountDto discountDto) {
        discountService.saveWithStores(discountDto);
        return R.success("Saved successfully");
    }
}
