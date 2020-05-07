package com.xy.cmbms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.Borinfo;
import com.xy.cmbms.entity.vos.BorVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:54
 */
@Repository
public interface BorinfoMapper extends BaseMapper<Borinfo> {

    List<BorVo> getAduitOrder();

    BorVo getOrderById(@Param("orderId") Integer orderId);

    void auditOrder(@Param("orderId") Integer orderId, @Param("userId") Integer userId, @Param("opinion") Integer opinion);

    //更改订单状态
    void updateOrderZt(@Param("orderId") Integer orderId);
}
