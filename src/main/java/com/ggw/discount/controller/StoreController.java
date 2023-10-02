package com.ggw.discount.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ggw.discount.common.BaseContext;
import com.ggw.discount.common.R;
import com.ggw.discount.dto.StoreDto;
import com.ggw.discount.entity.Store;
import com.ggw.discount.service.FavouriteService;
import com.ggw.discount.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;



    @PostMapping
    public R<String> add(@RequestBody Store store) {
        storeService.save(store);
        return R.success("Added successfully");
    }

    @PutMapping
    public R<String> update(@RequestBody Store store) {
        storeService.updateById(store);
        return R.success("Updated successfully");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        LambdaUpdateWrapper<Store> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Store::getIsDeleted, 1).eq(Store::getId, id);
        storeService.update(wrapper);
        return R.success("Deleted successfully");
    }

    @GetMapping("/page")
    public R<Page> getAllByPage(int page, int pageSize, Store store) {
        Page<Store> pageInfo = storeService.getAllByPage(page, pageSize, store);
        return R.success(pageInfo);
    }

    @GetMapping("/userpage")
    public R<Page> getAllWithFavouriteByPage(int page, int pageSize, Store store) {
        Page<StoreDto> dtoPage = storeService.getAllWithFavouriteByPage(page, pageSize, store);
        return R.success(dtoPage);
    }

    @GetMapping("/list")
    public R<List<Store>> getAllByList() {
        LambdaQueryWrapper<Store> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Store::getName).eq(Store::getIsDeleted, 0);
        return R.success(storeService.list(lambdaQueryWrapper));
    }

    @GetMapping
    public R<Store> getById(Long id) {
        return R.success(storeService.getById(id));
    }
}
