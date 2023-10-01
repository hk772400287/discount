package com.ggw.discount.favouriteService;

import com.ggw.discount.service.FavouriteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class favouriteServiceTest {

    @Autowired
    private FavouriteService favouriteService;

    @Test
    public void testListByCategory() {
        Set<Long> favouriteDiscounts = favouriteService.listByCategory(1, 1704119254811643906L);
        favouriteDiscounts.forEach(System.out::println);
    }
}
