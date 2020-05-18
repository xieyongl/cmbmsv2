package com.xy.cmbms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.cmbms.entity.dos.Borrow;
import com.xy.cmbms.entity.vos.BorVo;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:48
 */
public interface BorrowService extends IService<Borrow> {

    /**
     * 预定物资
     * @param borVo
     * @return
     */
    int reserveGoods(BorVo borVo);

    /**
     * 借用（借取）物资
     */
    int isOkborrowGoods(Integer userId, Integer flag, Integer orderId);

    /**
     * 归还物资
     */
    int returnGoods(Integer userId, Integer orderId);

    /*
        按条件获取订单
     */
    List<BorVo> getAllOrderByTerm(Integer borrowOpinion, String borrowTime, Integer orderId, Integer officeId);


    /*
        物资成功借出，改变订单状态
     */
    int borrowAfter(Integer orderId);

    /*
        根据用户id获取订单信息
     */
    List<BorVo> getOrderByuserId(Integer userId);

}
