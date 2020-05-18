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

    void updateActTime(@Param("orderId") Integer orderId);

    BorVo getOrderById(@Param("orderId") Integer orderId);

    void auditOrder(@Param("userId") Integer userId,
                    @Param("orderId") Integer orderId,
                    @Param("opinion") Integer opinion);

    void updateOrderType(@Param("orderId") Integer orderId, @Param("borrowType") int borrowType);

    //更改订单状态(是否有效)
    void updateOrderZt(@Param("orderId") Integer orderId);

    //获取订单状态
    int getOrderStatus(@Param("orderId") Integer orderId);

    //物资成功借出，改变订单状态
    void borrowAfter(@Param("orderId") Integer orderId);

    //根据用户id获取订单信息
    List<BorVo> getOrderByuserId(@Param("userId") Integer userId);
}
