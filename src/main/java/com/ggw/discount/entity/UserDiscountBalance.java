package com.ggw.discount.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserDiscountBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long discountId;

    private BigDecimal balanceAmount;
}

