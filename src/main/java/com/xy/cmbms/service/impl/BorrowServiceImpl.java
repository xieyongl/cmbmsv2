package com.xy.cmbms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cmbms.constant.BaseConstant;
import com.xy.cmbms.entity.dos.Borinfo;
import com.xy.cmbms.entity.dos.Borrow;
import com.xy.cmbms.entity.dos.Goods;
import com.xy.cmbms.entity.vos.BackGoodsVo;
import com.xy.cmbms.entity.vos.BorVo;
import com.xy.cmbms.entity.vos.BorrowVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.exception.BusinessException;
import com.xy.cmbms.mapper.BorinfoMapper;
import com.xy.cmbms.mapper.BorrowMapper;
import com.xy.cmbms.mapper.GoodsMapper;
import com.xy.cmbms.mapper.TypeGoodsMapper;
import com.xy.cmbms.service.BorrowService;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private GoodsMapper goodsMapper;

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
            int count = borrowMapper.queryNumber(b.getGoodsId());
            if (b.getBorrowNumber() > count) {
                throw new BusinessException(ErrorEnum.NOT_ENOUGH);
            }
        }

        //将申请表信息存入borinfo
        Borinfo borinfo = new Borinfo();
        dozerBeanMapper.map(borVo, borinfo);
        borinfo.setBorrowTime(new Date());
        borinfo.setDelFlag(BaseConstant.ONE);
        borinfoMapper.insert(borinfo);

        int borinfoId = borinfo.getId();


        //将物资申请信息存入borrow
        for (BorrowVo b : borList
                ) {
            if (b != null) {
                Borrow borrow = new Borrow();
                int typeId = b.getGoodsId();
                dozerBeanMapper.map(b, borrow);
                borrow.setBorinfoId(borinfoId);
                borrow.setDelFlag(BaseConstant.ONE);
                borrowMapper.insert(borrow);
                //将各个种类物资更新剩余数量（减去刚刚预定的）
                int goodsRemain = borrowMapper.queryNumber(b.getGoodsId());
                goodsRemain -= borrow.getBorrowNumber();
                typeGoodsMapper.updateRemain(typeId, goodsRemain);
            }
        }
        return borinfoId;
    }


    public int isOkborrowGoods(Integer userId, Integer flag, Integer orderId) {
        //成功借出
        if (flag == 1) {
            borrowMapper.borrowGoods(2, orderId);
            borinfoMapper.updateOrderType(orderId, 2);
            return 1;
        } else { //取消借用, 需将已预定的物资数量归为到相应物资种类
            borrowMapper.borrowGoods(3, orderId); //改变订单物资状态
            //获取订单物资详情
//            BackGoodsVo backGoods = borrowMapper.selectGoodsByOrder(orderId);
            List<Borrow> borrowList = borrowMapper.selectBorrowByBorinfoId(orderId);
            //将订单物资进行进行归位
            for (int i = 0; i < borrowList.size(); i++) {
                //获取该类物资的库存信息
                Goods goods = (Goods) typeGoodsMapper.selectGoodsById(borrowList.get(i).getGoodsId());
                int nowGoodsNum = goods.getGoodsRemain();
                int afterNum = borrowList.get(i).getBorrowNumber() + nowGoodsNum;
                typeGoodsMapper.goodsPlace(borrowList.get(i).getGoodsId(), afterNum);
            }
            //更改订单状态为作废
            borinfoMapper.updateOrderZt(orderId);
            return 0;
        }
    }

    public int returnGoods(Integer userId, Integer orderId) {
        //获取订单物资详情
        BackGoodsVo backGoods = borrowMapper.selectGoodsByOrder(orderId);
        List<Borrow> borrowList = borrowMapper.selectBorrowByBorinfoId(backGoods.getBorinfoId());
        //将订单物资进行进行归位
        for (int i = 0; i < borrowList.size(); i++) {
            //通过物资id获取物资剩余可借数
            Goods goods = goodsMapper.selectById(borrowList.get(i).getGoodsId());
            int remain = goods.getGoodsRemain();
            remain +=borrowList.get(i).getBorrowNumber();
            typeGoodsMapper.goodsPlace(borrowList.get(i).getGoodsId(), remain);
//            borinfoMapper.auditOrder();
        }
        //更改broinfo表的实际归还时间
        borinfoMapper.updateActTime(orderId);
        borinfoMapper.updateOrderType(orderId,3);
        return 1;
    }

    /*
    按条件获取订单
 */
    public List<BorVo> getAllOrderByTerm(Integer borrowOpinion, String borrowTime, Integer orderId, Integer officeId){
        List<BorVo> list = borrowMapper.getAllOrderByTerm(borrowOpinion, borrowTime, orderId, officeId);
        return list;
    }


    /*
       物资成功借出，改变订单状态
    */
    public int borrowAfter(Integer orderId) {
        //判断当前订单状态是否是预定成功还未借出
        int  status = borinfoMapper.getOrderStatus(orderId);
        if (status != 1) {
            throw new BusinessException(ErrorEnum.ORDER_NOT_RESERVE);
        }
        borinfoMapper.borrowAfter(orderId);
        return 1;
    }

    /*

        根据用户id获取订单信息
     */
    public List<BorVo> getOrderByuserId(Integer userId) {
        List<BorVo> list = new ArrayList<>();
        list = borinfoMapper.getOrderByuserId(userId);
        return list;
    }
}
