package com.xy.cmbms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.Borrow;
import com.xy.cmbms.entity.vos.BackGoodsVo;
import com.xy.cmbms.entity.vos.BorVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:53
 */
@Repository
public interface BorrowMapper extends BaseMapper<Borrow> {

    int queryNumber(@Param("goodsId") Integer goodsId);

    void auditOrder(@Param("orderId") Integer orderId, @Param("type") Integer type);

//    void auditBroinfo(@Param("userId") Integer userId, @Param("orderId") Integer orderId, @Param("opinion") Integer opinion);

    int borrowGoods(@Param("type") Integer type, @Param("orderId") Integer orderId);

    BackGoodsVo selectGoodsByOrder(@Param("orderId") Integer orderId);

    List<Borrow> selectBorrowByBorinfoId(@Param("borinfoId") Integer borinfoId);

    List<BorVo> getAllOrderByTerm(@Param("borrowOpinion") Integer borrowOpinion,
                                  @Param("borrowTime") String borrowTime,
                                  @Param("orderId") Integer orderId,
                                  @Param("officeId") Integer officeId);



}
