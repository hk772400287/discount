package com.ggw.discount.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ggw.discount.common.BaseContext;
import com.ggw.discount.common.R;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.*;
import com.ggw.discount.service.DiscountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;


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
        //Todo: Long userId = BaseContext.getCurrentId();
        Long userId = 1704119254814224412L;
        Page<Discount> discountPage = new Page<>(page, pageSize);
        Page<DiscountDto> discountDtoPage = discountService.getAllWithStoresForUser(discountDto, discountPage, userId);
        return R.success(discountDtoPage);
    }

    /**
     * Get the discount info including stores and user spending list.
     * @param discountId
     * @return
     */
    @GetMapping("/forUserDetail")
    public R<DiscountDto> getDiscountDetailForUser(Long discountId) {
        //Todo: Long userId = BaseContext.getCurrentId();
        Long userId = 1704119254814224412L;
        DiscountDto discountDto = discountService.getDiscountInfoWithSpendingById(discountId, userId);
        return R.success(discountDto);
    }


}
