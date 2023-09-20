package com.ggw.discount.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ggw.discount.common.BaseContext;
import com.ggw.discount.common.R;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.*;
import com.ggw.discount.service.DiscountService;
import com.ggw.discount.service.DiscountStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountStoreService discountStoreService;


    @PostMapping
    public R<String> add(@RequestBody DiscountDto discountDto) {
        discountService.saveWithStores(discountDto);
        return R.success("Saved successfully");
    }

    @PutMapping
    public R<String> update(@RequestBody DiscountDto discountDto) {
        discountService.updateWithStores(discountDto);
        return R.success("Updated successfully");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        discountService.deleteWithStores(id);
        return R.success("Deleted successfully");
    }

    @GetMapping("/forAdminByPage/{page}/{pageSize}")
    public R<Page> getAllForAdmin(@PathVariable int page, @PathVariable int pageSize, DiscountDto discountDto) {
        //discountDto: Include store name and discount description specified by the admin.
        Page<Discount> discountPage = new Page<>(page, pageSize);
        Page<DiscountDto> discountDtoPage = discountService.getAllWithStoresForAdmin(discountDto, discountPage);
        return R.success(discountDtoPage);
    }

    @GetMapping("/forUserByPage/{page}/{pageSize}")
    public R<Page> getAllForUser(@PathVariable int page, @PathVariable int pageSize, DiscountDto discountDto) {
        //discountDto: Include store name and discount description specified by the user.
        Long userId = BaseContext.getCurrentId();
        Page<Discount> discountPage = new Page<>(page, pageSize);
        Page<DiscountDto> discountDtoPage = discountService.getAllWithStoresForUser(discountDto, discountPage, userId);
        return R.success(discountDtoPage);
    }

    @GetMapping("/forUserDetail")
    public R<DiscountDto> getDiscountDetailForUser(Long discountId) {
        Long userId = BaseContext.getCurrentId();
        DiscountDto discountDto = new DiscountDto();

        return R.success(discountDto);
    }


}
