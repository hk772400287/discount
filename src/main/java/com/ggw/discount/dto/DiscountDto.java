package com.ggw.discount.dto;

import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.Store;
import com.ggw.discount.entity.UserDiscountSpending;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DiscountDto extends Discount {

    private List<Store> storeList;

    private BigDecimal balanceAmount;

    private Long storeId;

    //Store name
    private String name;

    //Store icon
    private String icon;

    private List<UserDiscountSpending> spendingList;

    // 0:no 1:yes
    private int isFavourite;

}
