package com.ggw.discount.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.Favourite;
import com.ggw.discount.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavouriteMapper extends BaseMapper<Favourite> {

    @Select("SELECT s.id, s.name, s.icon " +
            "FROM store s INNER JOIN favourite f " +
            "ON s.id=f.store_id " +
            "WHERE f.user_id=#{userId} AND f.store_id IS NOT NULL " +
            "ORDER BY f.create_time")
    List<Store> getFavouriteStores(Long userId, Page<Store> page);


    @Select("SELECT d.id " +
            "FROM discount d INNER JOIN favourite f " +
            "ON d.id=f.discount_id " +
            "WHERE f.user_id=#{userId} AND f.discount_id IS NOT NULL " +
            "ORDER BY f.create_time")
    List<Discount> getFavouriteDiscounts(Long userId, Page<Discount> page);
}
