package com.xy.cmbms.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cmbms.constant.BaseConstant;
import com.xy.cmbms.entity.dos.Goods;
import com.xy.cmbms.entity.dos.TypeGoods;
import com.xy.cmbms.entity.dtos.GoodsDto;
import com.xy.cmbms.entity.dtos.TypeGoodsDto;
import com.xy.cmbms.entity.vos.GoodsVo;
import com.xy.cmbms.entity.vos.UserVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.exception.BusinessException;
import com.xy.cmbms.mapper.GoodsMapper;
import com.xy.cmbms.mapper.TypeGoodsMapper;
import com.xy.cmbms.mapper.UserMapper;
import com.xy.cmbms.service.GoodsService;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Xieyong
 * @date 2019/12/16 - 12:50
 */
@Service
public class GoodsServiceImp extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private DozerBeanMapper dozerBeanMapper;

    @Resource
    private TypeGoodsMapper typeGoodsMapper;

    @Resource
    private UserMapper userMapper;


    /**
     * 增加物资类别(球类，桌椅类)
     */
    public int addGoodsType(GoodsVo goodsVo) {

        TypeGoods typeGoods = typeGoodsMapper.hasTypeGoods(goodsVo.getName());
        if (typeGoods != null) {
            throw new BusinessException(ErrorEnum.HAS_TYPE_GOODS);
        }
        TypeGoods temp = new TypeGoods();
        temp.setName(goodsVo.getName());
        typeGoodsMapper.insert(temp);
        int typeId = temp.getId();
        return typeId;
    }

    /**
     * 获取物资类别(球类，桌椅类)
     */
    public List<TypeGoods> getGoodsTypeList() {
        List<TypeGoods> list = new ArrayList<>();
        list = typeGoodsMapper.getGoodsTypeList();
        return list;
    }


    /**
     * 增加物资名称及数量(篮球 足球)
     */
    public Integer addGoods(GoodsVo goodsVo) {

        Integer officeId = goodsVo.getOfficeId();
        String goodsName = goodsVo.getGoodsName();

        //判断该类型物资在当前组织是否已存在
        Goods goods = typeGoodsMapper.hasGoods(officeId, goodsName);
        if (goods != null) {
            throw new BusinessException(ErrorEnum.HAS_TYPE_GOODS);
        }

        if (goodsVo.getGoodsTotal() < BaseConstant.ZERO) {
            throw new BusinessException(ErrorEnum.LESS_THAN_ZERO);
        }

        Goods newGoods = new Goods();
        newGoods.setTypeId(goodsVo.getTypeId());
        newGoods.setOfficeId(goodsVo.getOfficeId());
        newGoods.setGoodsName(goodsVo.getGoodsName());
        newGoods.setGoodsPic(goodsVo.getGoodsPic());
        newGoods.setGoodsTotal(goodsVo.getGoodsTotal());
        newGoods.setGoodsRemain(goodsVo.getGoodsTotal());
        newGoods.setCreateBy(goodsVo.getCreateBy());
        newGoods.setCreateDate(new Date());
        newGoods.setUpdateBy(goodsVo.getCreateBy());
        newGoods.setUpdateDate(new Date());
        newGoods.setStatus(BaseConstant.ONE);
        newGoods.setRemarks(goodsVo.getRemarks());
        newGoods.setDelFlag(BaseConstant.ONE);
        goodsMapper.insert(newGoods);
        int tempId = newGoods.getId();
        return tempId;
    }



    /**
     * 删除物资的类型及其数量，如需保留物资类型删除个别物资，则选择更改物资update
     * @param
     */
    @Override
    public Integer deleteGoods(Integer userId, Integer officeId, Integer id) {

        //判断当前物资类型是否存在
         Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new BusinessException(ErrorEnum.NO_THIS_GOODS);
        }
        //判断当前物资类型是否可以删除（物资剩余数是否等于物资总数）
        if (goods.getGoodsTotal() != goods.getGoodsRemain()) {
            throw new BusinessException(ErrorEnum.NO_DELETE);
        }
        Date updateDate = new Date();
        System.out.println("-----------------------------" + updateDate);

        //删除物资——将del_flag置为0，并将tbl_goods表的该机构类别的所有物资del_flag置为0
        typeGoodsMapper.deleteGoods(userId, updateDate, officeId, id);
        return 1;
    }


    /**
     * 删除物资大类
     */
    public int deleteGoodsType(Integer userId, Integer id) {
        //判断当前用户是否是管理员
        UserVo temp = userMapper.isAdmin(userId);
        if (temp == null) {
           return 0;
        }
        typeGoodsMapper.deleteGoodsType(id);
        return 1;
    }

    /**
     * 模糊查询物资
     * @param goodsName
     * @return
     */
    public Page<GoodsVo> queryTypeGoods(String goodsName, Integer pageNum, Integer pageSize) {
        Page<GoodsVo> page = new Page<>(pageNum, pageSize);
        List<GoodsVo> goodsVoList = typeGoodsMapper.queryTypeGoods(goodsName);
        page.setRecords(goodsVoList);
        return page;
    }


    /**
     * 修改物资
     */
    public boolean updateGoods(GoodsVo goodsVo) {

        return true;
    }

//    根据组织机构id获取组织所有物质种类
    public List<GoodsVo> getGoodsByOfficeId(String officeId) {
        List<GoodsVo> list = goodsMapper.getGoodsByOfficeId(officeId);
        return list;
    }

    /**
     * 获取所有机构所有物资信息
     */
    public List<GoodsVo> getAllGoods(Integer officeId, Integer goodsTypeId) {
        List<GoodsVo> list = goodsMapper.getAllGoods(officeId, goodsTypeId);
        return list;
    }

    /**
     * 更改物资数量
     */
    public void updateGoods(Integer flag, Integer goodsId, Integer updateNum, Integer userId, Integer goodsStatus, Integer goodsType) {
        //判断当前物资类型是否存在
        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            throw new BusinessException(ErrorEnum.NO_THIS_GOODS);
        }

        int goodsTotal = goods.getGoodsTotal();
        int goodsRemain = goods.getGoodsRemain();
        if (flag == null) {
            goodsMapper.updateGoods(goodsId, null, null, userId, goodsStatus, goodsType);
        } else if (flag == 1) {
            goodsTotal += updateNum;
            goodsRemain += updateNum;
            //如果是新增，直接在物资总数和剩余数添加数量就行
            goodsMapper.updateGoods(goodsId, goodsTotal, goodsRemain, userId, goodsStatus, goodsType);
        } else {
            //减少物资数量，需要判断当前物资的剩余可借数是否大于减去的数，如果小于，则不能减少
            if (goodsRemain < updateNum) {
                throw new BusinessException(ErrorEnum.CANNOT_DELETE);
            }
            goodsTotal -= updateNum;
            goodsRemain -= updateNum;
            goodsMapper.updateGoods(goodsId, goodsTotal, goodsRemain, userId, goodsStatus, goodsType);
        }
    }

}
