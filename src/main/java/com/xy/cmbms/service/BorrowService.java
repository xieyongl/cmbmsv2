package com.xy.cmbms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.cmbms.entity.dos.Borrow;
import com.xy.cmbms.entity.vos.BorVo;

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
    int borrowGoods(Integer userId, Integer flag, Integer orderId);

    /**
     * 归还物资
     */
    int returnGoods(Integer userId, Integer orderId);

}
