package com.ggw.discount.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ggw.discount.common.R;
import com.ggw.discount.entity.Store;
import com.ggw.discount.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping
    public R<String> delete(Long id) {
        storeService.removeById(id);
        return R.success("Deleted successfully");
    }

    @GetMapping("/page")
    public R<Page> getAll(int page, int pageSize, Store store) {
        Page<Store> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Store> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Store::getName);
        lambdaQueryWrapper.like(store.getName() != null, Store::getName, store.getName());
        storeService.page(pageInfo, lambdaQueryWrapper);
        return R.success(pageInfo);
    }
}
