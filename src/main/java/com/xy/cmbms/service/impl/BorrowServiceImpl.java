package com.xy.cmbms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cmbms.entity.dos.Borinfo;
import com.xy.cmbms.entity.dos.Borrow;
import com.xy.cmbms.entity.dos.TypeGoods;
import com.xy.cmbms.entity.vos.BackGoods;
import com.xy.cmbms.entity.vos.BorVo;
import com.xy.cmbms.entity.vos.BorrowVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.exception.BusinessException;
import com.xy.cmbms.mapper.BorinfoMapper;
import com.xy.cmbms.mapper.BorrowMapper;
import com.xy.cmbms.mapper.TypeGoodsMapper;
import com.xy.cmbms.service.BorrowService;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:53
 */
@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements BorrowService {

    @Resource
    private BorrowMapper borrowMapper;

    @Resource
    private BorinfoMapper borinfoMapper;

    @Resource
    private TypeGoodsMapper typeGoodsMapper;

    @Resource
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public int reserveGoods(BorVo borVo) {

        //判断所借物资数量是否满足需求
        List<BorrowVo> borList = borVo.getBorrowList();
        for (BorrowVo b : borList
                ) {
            //根据物资种类id判断物资剩余数是否满足需求
            int count = borrowMapper.queryNumber(b.getTypeId());
            if (b.getBorrowNumber() > count) {
                throw new BusinessException(ErrorEnum.NOT_ENOUGH);
            }
        }

        //将申请表信息存入borinfo
        Borinfo borinfo = new Borinfo();
        dozerBeanMapper.map(borVo, borinfo);
        borinfo.setBorrowTime(new Date());
        borinfoMapper.insert(borinfo);

        int borinfoId = borinfo.getId();


        //将物资申请信息存入物资种
        for (BorrowVo b : borList
                ) {
            if (b != null) {
                Borrow borrow = new Borrow();
                int typeId = b.getTypeId();
                dozerBeanMapper.map(b, borrow);
                borrow.setBorinfoId(borinfoId);
                borrowMapper.insert(borrow);
                //将各个种类物资更新剩余数量（减去刚刚预定的）
                int goodsRemain = borrowMapper.queryNumber(b.getTypeId());
                goodsRemain -= borrow.getBorrowNumber();
                typeGoodsMapper.updateRemain(typeId, goodsRemain);

            }
        }
        return borinfoId;
    }

    public int borrowGoods(Integer userId, Integer flag, Integer orderId) {
        //成功借出
        if (flag == 1) {
            borrowMapper.borrowGoods(2, orderId);
            return 1;
        } else { //取消借用, 需将已预定的物资数量归为到相应物资种类
            borrowMapper.borrowGoods(3, orderId); //改变订单物资状态
            //获取订单物资详情
//            BackGoods backGoods = borrowMapper.selectGoodsByOrder(orderId);
            List<Borrow> borrowList = borrowMapper.selectBorrowByBorinfoId(orderId);
            //将订单物资进行进行归位
            for (int i = 0; i < borrowList.size(); i++) {
                //获取该类物资的库存信息
                TypeGoods typeGoods = (TypeGoods) typeGoodsMapper.selectGoodsByTypeId(borrowList.get(i).getTypeId());
                int nowGoodsNum = typeGoods.getGoodsRemain();
                int afterNum = borrowList.get(i).getBorrowNumber() + nowGoodsNum;
                typeGoodsMapper.goodsPlace(borrowList.get(i).getTypeId(), afterNum);
            }
            //更改订单状态为作废
            borinfoMapper.updateOrderZt(orderId);
            return 0;
        }
    }

    public int returnGoods(Integer userId, Integer orderId) {
        //获取订单物资详情
        BackGoods backGoods = borrowMapper.selectGoodsByOrder(orderId);
        List<Borrow> borrowList = borrowMapper.selectBorrowByBorinfoId(backGoods.getBorinfoId());
        //将订单物资进行进行归位
        for (int i = 0; i < borrowList.size(); i++) {
            typeGoodsMapper.goodsPlace(borrowList.get(i).getTypeId(), borrowList.get(i).getBorrowNumber());
        }
        return 1;
    }
}
