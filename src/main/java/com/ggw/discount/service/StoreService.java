package com.ggw.discount.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ggw.discount.dto.StoreDto;
import com.ggw.discount.entity.Store;

public interface StoreService extends IService<Store> {

    Page<Store> getAllByPage(int page, int pageSize, Store store);

    Page<StoreDto> getAllWithFavouriteByPage(int page, int pageSize, Store store);
}
