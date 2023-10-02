package com.ggw.discount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.*;
import com.ggw.discount.mapper.DiscountMapper;
import com.ggw.discount.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl extends ServiceImpl<DiscountMapper, Discount> implements DiscountService {

    @Autowired
    private DiscountStoreService discountStoreService;

    @Autowired
    private UserDiscountBalanceService userDiscountBalanceService;

    @Autowired
    private UserDiscountSpendingService userDiscountSpendingService;

    @Autowired
    private UserService userService;

    @Autowired
    private FavouriteService favouriteService;

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
        //Save to user_discount_balance table.
        List<User> userList = userService.list();
        List<UserDiscountBalance> userDiscountBalanceList = userList.stream().map(user -> {
            UserDiscountBalance userDiscountBalance = new UserDiscountBalance();
            userDiscountBalance.setUserId(user.getId());
            userDiscountBalance.setDiscountId(discountId);
            userDiscountBalance.setBalanceAmount(discountDto.getMaxAmount());
            return userDiscountBalance;
        }).collect(Collectors.toList());
        userDiscountBalanceService.saveBatch(userDiscountBalanceList);
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
     * Get the discount info with store lists by specifying a discount id.
     * @param id: discountId
     * @return
     */
    @Override
    public DiscountDto getWithStoresById(Long id) {
        List<DiscountDto> discountDtoList = this.baseMapper.getWithStoresById(id);
        DiscountDto discountDto = new DiscountDto();
        System.out.println("*******discountDtoList:");
        discountDtoList.forEach(System.out::println);
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


    /**
     * Get the paged discount info with store list filtered by specified store name and discount description.
     * @param discountDtoQuery: Store name and discount description specified by the user.
     * @param page
     * @return
     */
    @Override
    public Page<DiscountDto> getAllWithStoresForAdmin(DiscountDto discountDtoQuery, Page<Discount> page) {
        //Get all the related discount ids.
        List<Long> discountIdList = this.baseMapper.getDiscountIdsWithStoresByStoreNameOrDesc(discountDtoQuery);
        if (discountIdList.isEmpty()) {
            return new Page<>();
        }
        //Get the discount info by ids.
        Page<Discount> discountPage = this.getByIdsByPage(page, discountIdList);
        //Get store lists and set to the return Dto page info.
        Page<DiscountDto> discountDtoPage = new Page<>();
        BeanUtils.copyProperties(discountPage, discountDtoPage, "records");
        List<Discount> discountList = discountPage.getRecords();
        List<DiscountDto> discountDtos = discountList.stream().map(((discount -> this.getWithStoresById(discount.getId())))).collect(Collectors.toList());
        discountDtoPage.setRecords(discountDtos);
        return discountDtoPage;
    }


    @Override
    public Page<DiscountDto> getAllWithStoresForUser(DiscountDto discountDtoQuery, Page<Discount> page, Long userId) {
        List<Long> discountIdList = this.baseMapper.getUnexpiredDiscountIdsWithStoresByStoreNameOrDesc(discountDtoQuery);
        if (discountIdList.isEmpty()) {
            return new Page<>();
        }
        Page<Discount> discountPage = this.getByIdsByPage(page, discountIdList);
        //Get store lists & user balance and set to the return Dto page info.
        return this.convertDiscountPageToDiscountDtoPage(discountPage, userId);
    }


    /**
     * Set store lists and user balance to convert the discountPage to discountDtoPage.
     * @param discountPage
     * @param userId
     * @return
     */
    @Override
    public Page<DiscountDto> convertDiscountPageToDiscountDtoPage(Page<Discount> discountPage, Long userId) {
        Page<DiscountDto> discountDtoPage = new Page<>();
        BeanUtils.copyProperties(discountPage, discountDtoPage, "records");
        List<Discount> discountList = discountPage.getRecords();
        List<DiscountDto> discountDtos = discountList.stream().map(((discount -> {
            //Set store lists.
            Long discountId = discount.getId();
            DiscountDto discountDto = this.getWithStoresById(discountId);
            //Set user balance.
            BigDecimal balance = userDiscountBalanceService.getBalanceById(discountId, userId);
            discountDto.setBalanceAmount(balance);
            //Get user's favourite discount list and set isFavourite.
            Set<Long> favouriteDiscounts = favouriteService.listByCategory(1, userId);
            if (favouriteDiscounts.contains(discountId)) {
                discountDto.setIsFavourite(1);
            }
            return discountDto;
        }))).collect(Collectors.toList());
        discountDtoPage.setRecords(discountDtos);
        return discountDtoPage;
    }

    private Page<Discount> getByIdsByPage(Page<Discount> page, List<Long> discountIdList) {
        LambdaQueryWrapper<Discount> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Discount::getId, discountIdList).orderByDesc(Discount::getStart);
        return this.page(page, wrapper);
    }

    @Override
    public DiscountDto getDiscountInfoWithSpendingById(Long discountId, Long userId) {
        //Get discount basic info with stores.
        DiscountDto discountDto = getWithStoresById(discountId);
        //Set user balance.
        BigDecimal balance = userDiscountBalanceService.getBalanceById(discountId, userId);
        discountDto.setBalanceAmount(balance);
        //Get user's favourite discount list and set isFavourite.
        Set<Long> favouriteDiscounts = favouriteService.listByCategory(1, userId);
        if (favouriteDiscounts.contains(discountId)) {
            discountDto.setIsFavourite(1);
        }
        //Set user spending list.
        List<UserDiscountSpending> userDiscountSpendingList = userDiscountSpendingService.
                getSpendingListById(discountId, userId);
        discountDto.setSpendingList(userDiscountSpendingList);
        return discountDto;
    }


}
