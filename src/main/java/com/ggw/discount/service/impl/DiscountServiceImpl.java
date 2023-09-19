package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl extends ServiceImpl<DiscountMapper, Discount> implements DiscountService {

    @Autowired
    private DiscountStoreService discountStoreService;

    /**
     * Save the discount info to discount table and discount_store table.
     * @param discountDto
     */
    @Override
    @Transactional
    public void saveWithStores(DiscountDto discountDto) {
        //Save to Discount table.
        this.save(discountDto);
        //Save to Discount_Store table.
        List<Store> storeList = discountDto.getStoreList();
        Long discountId = discountDto.getId();
        saveToDiscountStore(discountId, storeList);
    }

    @Override
    @Transactional
    public void updateWithStores(DiscountDto discountDto) {
        this.updateById(discountDto);
        LambdaQueryWrapper<DiscountStore> wrapper = new LambdaQueryWrapper<>();
        Long discountId = discountDto.getId();
        wrapper.eq(DiscountStore::getDiscountId, discountId);
        discountStoreService.remove(wrapper);
        List<Store> storeList = discountDto.getStoreList();
        saveToDiscountStore(discountId, storeList);
    }

    private void saveToDiscountStore(Long discountId, List<Store> storeList) {
        List<DiscountStore> discountStoreList = storeList.stream().map((store -> {
            Long storeId = store.getId();
            DiscountStore discountStore = new DiscountStore();
            discountStore.setDiscountId(discountId);
            discountStore.setStoreId(storeId);
            return discountStore;
        })).collect(Collectors.toList());
        discountStoreService.saveBatch(discountStoreList);
    }

    /**
     * Get the paged discount info with store list filtered by specified store name and discount description.
     * @param discountDtoQuery: Store name and discount description specified by the user.
     * @param page
     * @return
     */
    @Override
    public Page<DiscountDto> getAllWithStoresBySpecifyStoreNameOrDescriptionByPage(DiscountDto discountDtoQuery, Page<Discount> page) {
        List<DiscountDto> discountDtoList = this.baseMapper.getAllWithStoresBySpecifyStoreNameOrDescription(discountDtoQuery);
        //Get all the related discount ids.
        Set<Long> ids = new HashSet<>();
        discountDtoList.forEach((discountDto) -> {
            Long id = discountDto.getId();
            ids.add(id);
        });
        //Get the discount info by ids.
        LambdaQueryWrapper<Discount> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Discount::getId, ids).orderByDesc(Discount::getStart);
        Page<Discount> discountPage = this.page(page, wrapper);
        //Get store lists and set to the return Dto page info.
        Page<DiscountDto> discountDtoPage = new Page<>();
        BeanUtils.copyProperties(discountPage, discountDtoPage, "records");
        List<Discount> discountList = discountPage.getRecords();
        List<DiscountDto> discountDtos = discountList.stream().map(((discount -> this.getWithStoresById(discount.getId())))).collect(Collectors.toList());
        discountDtoPage.setRecords(discountDtos);
        return discountDtoPage;
    }

    /**
     * Get the discount info with store lists by specifying an discount id.
     * @param id: discountId
     * @return
     */
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

    @Override
    public void deleteWithStores(Long id) {
        //Set is_deleted = 1 in discount table.
        LambdaUpdateWrapper<Discount> discountWrapper = new LambdaUpdateWrapper<>();
        discountWrapper.eq(Discount::getId, id).set(Discount::getIsDeleted, 1);
        this.update(discountWrapper);
        //Set is_deleted = 1 in discount_store table.
        LambdaUpdateWrapper<DiscountStore> discountStoreWrapper = new LambdaUpdateWrapper<>();
        discountStoreWrapper.set(DiscountStore::getIsDeleted, 1).eq(DiscountStore::getDiscountId, id);
        discountStoreService.update(discountStoreWrapper);
    }


}
