package com.xy.cmbms.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.cmbms.entity.dos.Goods;
import com.xy.cmbms.entity.dos.TypeGoods;
import com.xy.cmbms.entity.vos.GoodsVo;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:50
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 增加物资类别(球类，桌椅类)
     */
    int addGoodsType(GoodsVo goodsVo);

    /**
     * 获取物资类别(球类，桌椅类)
     */
    List<TypeGoods> getGoodsTypeList();


    /**
     * 增加物资名称(篮球 足球)
     */
    Integer addGoods(GoodsVo goodsVo);

    /**
     * 删除物资
     */
    Integer deleteGoods(Integer userId, Integer officeId, Integer id);

    /**
     * 删除物资大类
     */
    int deleteGoodsType(Integer userId, Integer id);


    /**
     * 模糊查询物资
     */
    Page<GoodsVo> queryTypeGoods(String goodsName, Integer pageNum, Integer pageSize);

        /**
         * 修改物资
         */
    boolean updateGoods(GoodsVo goodsVo);

//    根据组织机构id获取组织所有物质种类
    List<GoodsVo> getGoodsByOfficeId(String officeId);


    /**
     * 获取所有机构所有物资信息
     */
    List<GoodsVo> getAllGoods(Integer officeId, Integer goodsTypeId);

    /**
     * 更改物资数量
     */
    void updateGoods(Integer flag, Integer goodsId, Integer updateNum, Integer userId, Integer goodsStatus, Integer goodsType);
}
