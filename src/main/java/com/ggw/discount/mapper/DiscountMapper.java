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

    /**
     * Inner join discount table, discount_store table and store table,
     * get the records specified by store name and discount description.
     * @param discountDto
     * @return
     */
    @Select("""
               <script>
                    SELECT d.id, d.description, d.start, d.end, d.max_amount, s.name, s.icon
                    FROM discount d INNER JOIN discount_store ds ON d.id = ds.discount_id
                    INNER JOIN store s ON ds.store_id = s.id
                    <where>
                        d.is_deleted = 0
                        <if test="name != null">
                            and name like CONCAT(CONCAT('%', #{name}), '%')
                        </if>
                        <if test="description != null">
                            and description like CONCAT(CONCAT('%', #{description}), '%')
                        </if>
                    </where>
                    ORDER BY d.start DESC
                </script>
            """)
    List<DiscountDto> getAllWithStoresBySpecifyStoreNameOrDescription(DiscountDto discountDto);


    /**
     * Inner join discount table, discount_store table and store table,
     * specify the discount id in order to find the stores associated with the discount.
     * @param id
     * @return
     */
    @Select("SELECT d.id, d.description, d.start, d.end, d.max_amount, ds.store_id, s.name, s.icon " +
        "FROM discount d INNER JOIN discount_store ds ON d.id = ds.discount_id " +
        "INNER JOIN store s ON ds.store_id = s.id " +
        "WHERE d.id = #{id}")
    List<DiscountDto> getWithStoresById(Long id);
}
