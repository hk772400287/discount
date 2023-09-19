package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.DiscountStore;
import com.ggw.discount.entity.Store;
import com.ggw.discount.mapper.DiscountMapper;
import com.ggw.discount.service.DiscountService;
import com.ggw.discount.service.DiscountStoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl extends ServiceImpl<DiscountMapper, Discount> implements DiscountService {

    @Autowired
    private DiscountStoreService discountStoreService;

    @Override
    public void saveWithStores(DiscountDto discountDto) {
        //Save to Discount table.
        this.save(discountDto);
        //Save to Discount_Store table.
        List<Store> storeList = discountDto.getStoreList();
        Long discountId = discountDto.getId();
        List<DiscountStore> discountStoreList = storeList.stream().map((store -> {
            Long storeId = store.getId();
            DiscountStore discountStore = new DiscountStore();
            discountStore.setDiscountId(discountId);
            discountStore.setStoreId(storeId);
            return discountStore;
        })).collect(Collectors.toList());
        discountStoreService.saveBatch(discountStoreList);
    }

    @Override
    public Page<DiscountDto> getAllWithStoresBySpecifyStoreNameByPage(Store store, Page<Discount> page) {
        List<DiscountDto> discountDtoList = this.baseMapper.getAllWithStoresBySpecifyStoreName(store);
        Set<Long> ids = new HashSet<>();
        discountDtoList.forEach((discountDto) -> {
            Long id = discountDto.getId();
            ids.add(id);
        });
        LambdaQueryWrapper<Discount> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Discount::getId, ids).orderByDesc(Discount::getStart);
        Page<Discount> discountPage = this.page(page, wrapper);
        Page<DiscountDto> discountDtoPage = new Page<>();
        BeanUtils.copyProperties(discountPage, discountDtoPage, "records");
        List<Discount> discountList = discountPage.getRecords();
        List<DiscountDto> discountDtos = discountList.stream().map(((discount -> this.getWithStoresById(discount.getId())))).collect(Collectors.toList());
        discountDtoPage.setRecords(discountDtos);
        return discountDtoPage;
    }

    @Override
    public DiscountDto getWithStoresById(Long id) {
        List<DiscountDto> discountDtoList = this.baseMapper.getWithStoresById(id);
        DiscountDto discountDto = new DiscountDto();
        BeanUtils.copyProperties(discountDtoList.get(0), discountDto, "storeId", "name", "icon");
        List<Store> storeList = discountDtoList.stream().map((discountDto1 -> {
            Store store = new Store();
            store.setId(discountDto1.getStoreId());
            store.setName(discountDto1.getName());
            store.setIcon(discountDto1.getIcon());
            return store;
        })).collect(Collectors.toList());
        discountDto.setStoreList(storeList);
        return discountDto;
    }


}
