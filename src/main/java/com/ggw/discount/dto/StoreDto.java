package com.ggw.discount.dto;

import com.ggw.discount.entity.Store;
import lombok.Data;

@Data
public class StoreDto extends Store {

    // 0:no 1:yes
    private int isFavourite;
}
