package com.ggw.discount.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.Store;

import java.util.List;

public interface DiscountService extends IService<Discount> {

    void saveWithStores(DiscountDto discountDto);

    Page<DiscountDto> getAllWithStoresBySpecifyStoreNameByPage(Store store, Page<Discount> page);

    DiscountDto getWithStoresById(Long id);
}
