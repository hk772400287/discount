package com.ggw.discount.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @GetMapping("/page")
    public R<Page> getAllForAdmin(int page, int pageSize, Store store) {
//        Page<Discount> pageInfo = new Page<>(page, pageSize);
//        LambdaQueryWrapper<Discount> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrappe
//        discountService.page(pageInfo);
//
//        return R.success(pageInfo);
        return null;
    }

    @PostMapping
    public R<String> add(@RequestBody DiscountDto discountDto) {
        //Save to Discount table.
        discountService.save(discountDto);
        //Save to Discount_Store table.
        List<Store> storeList = discountDto.getStoreList();
        Long discountId = discountDto.getId();
        List<DiscountStore> discountStoreList = storeList.stream().map((store -> {
            Long storeId = store.getId();
            DiscountStore discountStore = new DiscountStore();
            discountStore.setDiscountId(discountId);
            discountStore.setStoreId(storeId);
            return discountStore;
        })).collect(Collectors.toList());
        discountStoreService.saveBatch(discountStoreList);
        return R.success("Saved successfully");
    }
}
