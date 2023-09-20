package com.ggw.discount.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class UserDiscountSpending implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long discountId;

    private Long storeId;

    private String storeName;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private BigDecimal amount;
}
