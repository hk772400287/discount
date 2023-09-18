package com.ggw.discount.dto;

import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.Store;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DiscountDto extends Discount {

    private List<Store> storeList;

    private BigDecimal balanceAmount;

}
