package com.ggw.discount.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ggw.discount.common.BaseContext;
import com.ggw.discount.common.R;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.Favourite;
import com.ggw.discount.service.FavouriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping("/favourite")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @PostMapping
    public R<String> add(@RequestBody Favourite favourite) {
        favourite.setCreateTime(LocalDateTime.now());
        favouriteService.save(favourite);
        return R.success("Saved successfully");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        favouriteService.removeById(id);
        return R.success("Deleted successfully");
    }

    /**
     *
     * @param page
     * @param pageSize
     * @param category: 0: store, 1: discount.
     * @return
     */
    @GetMapping("/{page}/{pageSize}")
    public R<Page> getAllByCategory(@PathVariable int page, @PathVariable int pageSize, int category) {
        Long userId = BaseContext.getCurrentId();
        //Long userId = 1704119254814224412L;
        Page pageInfo = favouriteService.getAllByCategory(page, pageSize, category, userId);
        return R.success(pageInfo);
    }
}
