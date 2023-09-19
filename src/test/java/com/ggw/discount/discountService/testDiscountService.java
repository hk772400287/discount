package com.ggw.discount.discountService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.Store;
import com.ggw.discount.service.DiscountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class testDiscountService {

    @Autowired
    private DiscountService discountService;

    @Test
    public void testGetWithStoresById() {
        Long id = 1703687930355318786L;
        DiscountDto discountDto = discountService.getWithStoresById(id);
        System.out.println(discountDto);
        Discount discount = (Discount) discountDto;
        System.out.println(discount.getDescription());
    }

    @Test
    public void testGetAllWithStoresBySpecifyStoreName() {
        Store store = new Store();
        store.setName("711");
        //Page<DiscountDto> page = discountService.getAllWithStoresBySpecifyStoreNameByPage(store, new Page<Discount>(1, 2));
        //log.info(page.toString());
    }
}
