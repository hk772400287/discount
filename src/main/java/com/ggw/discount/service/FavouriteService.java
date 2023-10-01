package com.ggw.discount.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ggw.discount.entity.Favourite;

import java.util.List;
import java.util.Set;

public interface FavouriteService extends IService<Favourite> {
    /**
     *
     * @param page
     * @param pageSize
     * @param category: 0: store, 1: discount.
     * @param userId
     * @return
     */
    Page getAllByCategory(int page, int pageSize, int category, Long userId);

    Set<Long> listByCategory(int category, Long userId);
}
