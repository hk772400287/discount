package com.ggw.discount.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ggw.discount.entity.Favourite;

public interface FavouriteService extends IService<Favourite> {
    Page getAllByCategory(int page, int pageSize, int category, Long userId);
}
