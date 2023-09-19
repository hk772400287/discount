package com.ggw.discount.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ggw.discount.dto.DiscountDto;
import com.ggw.discount.entity.Discount;
import com.ggw.discount.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DiscountMapper extends BaseMapper<Discount> {


    @Select("""
               <script>
                    SELECT d.id, d.description, d.start, d.end, d.max_amount, s.name, s.icon
                    FROM discount d INNER JOIN discount_store ds ON d.id = ds.discount_id
                    INNER JOIN store s ON ds.store_id = s.id
                    <where>
                        <if test="name != null">
                            and name like CONCAT(CONCAT('%', #{name}), '%')
                        </if>
                    </where>
                    ORDER BY d.start DESC
                </script>
            """)
    List<DiscountDto> getAllWithStoresBySpecifyStoreName(Store store);



    @Select("SELECT d.id, d.description, d.start, d.end, d.max_amount, ds.store_id, s.name, s.icon " +
        "FROM discount d INNER JOIN discount_store ds ON d.id = ds.discount_id " +
        "INNER JOIN store s ON ds.store_id = s.id " +
        "WHERE d.id = #{id}")
    List<DiscountDto> getWithStoresById(Long id);
}
