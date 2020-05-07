package com.xy.cmbms.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cmbms.constant.BaseConstant;
import com.xy.cmbms.entity.dos.Goods;
import com.xy.cmbms.entity.dos.TypeGoods;
import com.xy.cmbms.entity.dtos.GoodsDto;
import com.xy.cmbms.entity.dtos.TypeGoodsDto;
import com.xy.cmbms.entity.vos.GoodsVo;
import com.xy.cmbms.entity.vos.TypeGoodsVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.exception.BusinessException;
import com.xy.cmbms.mapper.GoodsMapper;
import com.xy.cmbms.mapper.TypeGoodsMapper;
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

    /**
     * 增加物资类别及数量
     */
    public Integer addGoodsType(TypeGoodsVo typeGoodsVo) {

        Integer officeId = typeGoodsVo.getOfficeId();
        String typeName = typeGoodsVo.getTypeName();

        //判断该类别物资在当前组织是否已存在
        TypeGoods tg = typeGoodsMapper.hasTypeGoods(officeId, typeName);
        if (tg != null) {
            throw new BusinessException(ErrorEnum.HAS_TYPE_GOODS);
        }

        if (typeGoodsVo.getGoodsTotal() < BaseConstant.ZERO) {
            throw new BusinessException(ErrorEnum.LESS_THAN_ZERO);
        }

        TypeGoods typeGoods = new TypeGoods();
        dozerBeanMapper.map(typeGoodsVo, typeGoods);

        typeGoods.setOfficeId(typeGoods.getOfficeId());
        typeGoods.setTypeName(typeGoods.getTypeName());
        typeGoods.setGoodsPic(typeGoods.getGoodsPic());
        typeGoods.setGoodsTotal(typeGoods.getGoodsTotal());
        typeGoods.setGoodsRemain(typeGoods.getGoodsTotal());
        typeGoods.setCreateBy(typeGoods.getCreateBy());
        typeGoods.setCreateDate(new Date());
        typeGoods.setUpdateBy(typeGoods.getCreateBy());
        typeGoods.setUpdateDate(new Date());
        typeGoods.setStatus(BaseConstant.ONE);
        typeGoods.setRemarks(typeGoods.getRemarks());
        typeGoods.setDelFlag(BaseConstant.ONE);
        typeGoodsMapper.insert(typeGoods);


        int[] count = new int[typeGoodsVo.getGoodsTotal()];
        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i<count.length; i++) {
            Goods goods = new Goods();
            goodsList.add(goods);
        }

        for (int i = 0; i < goodsList.size(); i++) {
            Goods tempGoods = goodsList.get(i);
            tempGoods.setTypeId(typeGoods.getId());
            tempGoods.setCreateBy(typeGoods.getCreateBy());
            tempGoods.setCreateDate(new Date());
//            tempGoods.setUpdateBy(typeGoods.getUpdateBy());
            tempGoods.setUpdateDate(new Date());
            tempGoods.setGoodsStatus(BaseConstant.ONE);
            tempGoods.setRemarks(typeGoods.getRemarks());
            tempGoods.setDelFlag(BaseConstant.ONE);
            goodsMapper.insert(tempGoods);
        }

        return typeGoods.getId();
    }


    /**
     * 删除物资
     *
     * @param id 物资id
     */
    @Override
    public Integer deleteGoods(Integer userId, Integer id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new BusinessException(ErrorEnum.NO_THIS_GOODS);
        }
        Date updateDate = new Date();
        System.out.println("-----------------------------" + updateDate);
        goodsMapper.deleteGoods(userId, id, updateDate);
        return 1;
    }


    /**
     * 删除物资的类型及其数量，如需保留物资类型删除个别物资，则选择更改物资update
     * @param
     */
    @Override
    public Integer deleteTypeGoods(TypeGoodsVo typeGoodsVo) {

        Integer id = typeGoodsVo.getId();
        Integer officeid = typeGoodsVo.getOfficeId();
        Integer userId = typeGoodsVo.getUserId();

        //判断当前物资类型是否存在
         TypeGoods typeGoods = typeGoodsMapper.selectById(id);
        if (typeGoods == null) {
            throw new BusinessException(ErrorEnum.NO_TYPE_GOODS);
        }
        //判断当前物资类型是否可以删除（物资剩余数是否等于物资总数）
        if (typeGoods.getGoodsTotal() != typeGoods.getGoodsRemain()) {
            throw new BusinessException(ErrorEnum.NO_DELETE);
        }
        Date updateDate = new Date();
        System.out.println("-----------------------------" + updateDate);

        //删除物资——将del_flag置为0，并将tbl_goods表的该机构类别的所有物资del_flag置为0
        typeGoodsMapper.deleteTypeGoods(userId, updateDate, officeid, id);
        goodsMapper.deleteTypeAllGoods(userId, id, updateDate);
        return 1;
    }


    /**
     * 查询单个物资
     */
    public GoodsVo selectGoods(Integer id) {

        Goods goods = goodsMapper.selectById(id);
        GoodsVo goodsVo = new GoodsVo();
        if(StringUtils.isEmpty(goods)) {
            throw new BusinessException(ErrorEnum.NO_THIS_GOODS);
        }
        dozerBeanMapper.map(goods,goodsVo);
        return goodsVo;
    }

    /**
     * 查询种类物资
     */
    public List<GoodsVo> selectGoodsByTypeId(Integer typeId) {
        List<GoodsVo> goodsVoList = new ArrayList<>();
        List<GoodsDto> goodsDtoList = goodsMapper.selectGoodsByTypeId(typeId);
        for (int i=0; i<goodsDtoList.size();i++) {
            GoodsVo vo = new GoodsVo();
            GoodsDto dto = goodsDtoList.get(i);
            if (dto != null) {
                dozerBeanMapper.map(dto,vo);
                goodsVoList.add(vo);
            }
        }
        return goodsVoList;
    }

    /**
     * 模糊查询物资
     * @param goodsName
     * @return
     */
    public Page<TypeGoodsVo> queryTypeGoods(String goodsName, Integer pageNum, Integer pageSize) {
        Page<TypeGoodsVo> page = new Page<>(pageNum, pageSize);
        List<TypeGoodsDto> typeGoodsDtoList = typeGoodsMapper.queryTypeGoods(goodsName);
        List<TypeGoodsVo> typeGoodsVoList = new ArrayList<>();
        for (int i=0; i<typeGoodsDtoList.size(); i++) {
            TypeGoodsDto dto = typeGoodsDtoList.get(i);
            TypeGoodsVo typeGoodsVo = new TypeGoodsVo();
            dozerBeanMapper.map(dto,typeGoodsVo);
            typeGoodsVoList.add(typeGoodsVo);
        }
        page.setRecords(typeGoodsVoList);
        return page;
    }




    /**
     * 修改物资
     */
    public boolean updateGoods(Goods goods) {

        return true;
    }

//    根据组织机构id获取组织所有物质种类
    public List<TypeGoodsVo> getGoodsTypeByOfficeId(String officeId) {
        List<TypeGoodsVo> list = goodsMapper.getGoodsTypeByOfficeId(officeId);
        return list;
    }


}
