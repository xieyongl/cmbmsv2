package com.xy.cmbms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.Borrow;
import com.xy.cmbms.entity.vos.BackGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:53
 */
@Repository
public interface BorrowMapper extends BaseMapper<Borrow> {

    int queryNumber(@Param("typeId") Integer typeId);

    void auditOrder(@Param("orderId") Integer orderId, @Param("type") Integer type);


    int borrowGoods(@Param("type") Integer type, @Param("orderId") Integer orderId);

    BackGoods selectGoodsByOrder(@Param("orderId") Integer orderId);

    List<Borrow> selectBorrowByBorinfoId(@Param("borinfoId") Integer borinfoId);

}
