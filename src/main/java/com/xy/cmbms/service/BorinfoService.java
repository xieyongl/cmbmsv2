package com.xy.cmbms.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.cmbms.entity.dos.Borinfo;
import com.xy.cmbms.entity.vos.BorVo;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:49
 */
public interface BorinfoService extends IService<Borinfo> {

    /**
     * 获取待审核订单
     */
    List<BorVo> getAduitOrder();

    /**
     * 审核订单
     */
    int auditOrder(Integer userId, Integer orderId, Integer opinion);



}
