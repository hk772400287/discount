package com.ggw.discount.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.Store;

import java.util.List;

public interface DiscountService extends IService<Discount> {

    void saveWithStores(DiscountDto discountDto);

    void updateWithStores(DiscountDto discountDto);

    Page<DiscountDto> getAllWithStoresForAdmin(DiscountDto discountDto, Page<Discount> page);

    DiscountDto getWithStoresById(Long id);

    void deleteWithStores(Long id);

    Page<DiscountDto> getAllWithStoresForUser(DiscountDto discountDto, Page<Discount> discountPage, Long userId);
}
