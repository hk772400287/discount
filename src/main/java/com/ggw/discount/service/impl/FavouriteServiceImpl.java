package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.Favourite;
import com.ggw.discount.entity.Store;
import com.ggw.discount.mapper.FavouriteMapper;
import com.ggw.discount.service.DiscountService;
import com.ggw.discount.service.FavouriteService;
import com.ggw.discount.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavouriteServiceImpl extends ServiceImpl<FavouriteMapper, Favourite> implements FavouriteService {

    @Autowired
    private StoreService storeService;

    @Autowired
    private DiscountService discountService;

    @Override
    public Page getAllByCategory(int page, int pageSize, int category, Long userId) {
        LambdaQueryWrapper<Favourite> favouriteWrapper = new LambdaQueryWrapper<>();
        favouriteWrapper.eq(Favourite::getUserId, userId);
        if (category == 0) {
            //Select favourite stores.
            Page<Store> storePage = new Page<>(page, pageSize);
            List<Store> storeList= this.baseMapper.getFavouriteStores(userId, storePage);
            storePage.setRecords(storeList);
            return storePage;
        }
        //Select favourite discounts.
        Page<Discount> discountPage = new Page<>(page, pageSize);
        List<Discount> discountList = this.baseMapper.getFavouriteDiscounts(userId, discountPage);
        discountPage.setRecords(discountList);
        return discountService.convertDiscountPageToDiscountDtoPage(discountPage, userId);
    }
}
