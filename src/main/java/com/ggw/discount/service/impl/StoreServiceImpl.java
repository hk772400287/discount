package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.common.BaseContext;
import com.ggw.discount.dto.StoreDto;
import com.ggw.discount.entity.Store;
import com.ggw.discount.mapper.StoreMapper;
import com.ggw.discount.service.FavouriteService;
import com.ggw.discount.service.StoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {

    @Autowired
    private FavouriteService favouriteService;


    @Override
    public Page<Store> getAllByPage(int page, int pageSize, Store store) {
        Page<Store> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Store> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Store::getName).eq(Store::getIsDeleted, 0);
        lambdaQueryWrapper.like(store.getName() != null, Store::getName, store.getName());
        this.page(pageInfo, lambdaQueryWrapper);
        return pageInfo;
    }

    @Override
    public Page<StoreDto> getAllWithFavouriteByPage(int page, int pageSize, Store store) {
        Page<Store> storePage = this.getAllByPage(page, pageSize, store);
        Long userId = BaseContext.getCurrentId();
        Set<Long> favouriteStoreList = favouriteService.listByCategory(0, userId);
        Page<StoreDto> storeDtoPage = new Page<>();
        BeanUtils.copyProperties(storePage, storeDtoPage, "records");
        List<Store> storeList = storePage.getRecords();
        List<StoreDto> storeDtoList = storeList.stream().map(store1 -> {
            StoreDto storeDto = new StoreDto();
            BeanUtils.copyProperties(store1, storeDto);
            Long store1Id = store1.getId();
            storeDto.setIsFavourite(favouriteStoreList.contains(store1Id) ? 1 : 0);
            return storeDto;
        }).collect(Collectors.toList());
        storeDtoPage.setRecords(storeDtoList);
        return storeDtoPage;
    }
}
