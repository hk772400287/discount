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
    public R<Page> getAllForAdmin(@PathVariable int page, @PathVariable int pageSize, DiscountDto discountDto) {
        //discountDto: store name, discount description.
        Page<Discount> discountPage = new Page<>(page, pageSize);
        Page<DiscountDto> discountDtoPage = discountService.getAllWithStoresBySpecifyStoreNameOrDescriptionByPage(discountDto, discountPage);
        return R.success(discountDtoPage);
    }

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
}
