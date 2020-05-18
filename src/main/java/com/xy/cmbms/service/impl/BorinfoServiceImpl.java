package com.xy.cmbms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cmbms.entity.dos.Borinfo;
import com.xy.cmbms.entity.vos.BorVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.exception.BusinessException;
import com.xy.cmbms.mapper.BorinfoMapper;
import com.xy.cmbms.mapper.BorrowMapper;
import com.xy.cmbms.service.BorinfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:52
 */
@Service
public class BorinfoServiceImpl extends ServiceImpl<BorinfoMapper, Borinfo> implements BorinfoService {

    @Resource
    private BorinfoMapper borinfoMapper;

    @Resource
    private BorrowMapper borrowMapper;

    public List<BorVo> getAduitOrder() {
        List<BorVo> list = borinfoMapper.getAduitOrder();
        return list;
    }

    public int auditOrder(Integer userId, Integer orderId, Integer opinion) {
        BorVo order = borinfoMapper.getOrderById(orderId);
        if(StringUtils.isEmpty(order.getReturnTime())) {
            throw new BusinessException(ErrorEnum.RETURNTIME_IS_NULL);
        }
        borinfoMapper.auditOrder(userId, orderId, opinion);
        if (opinion == 0) {//不同意
            borrowMapper.auditOrder(orderId, 3);
        } else { //1.同意
            //更改borrow表
            borrowMapper.auditOrder(orderId, 1);
            borinfoMapper.updateOrderType(orderId, 1);
        }
        return 1;
    }

}
