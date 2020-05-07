package com.xy.cmbms.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.cmbms.entity.dos.Goods;
import com.xy.cmbms.entity.vos.GoodsVo;
import com.xy.cmbms.entity.vos.TypeGoodsVo;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:50
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 增加物资类别
     */
    Integer addGoodsType(TypeGoodsVo typeGoodsVo);

    /**
     * 删除物资
     */
    Integer deleteGoods(Integer userId, Integer id);

    /**
     * 删除物资类别
     */
    Integer deleteTypeGoods(TypeGoodsVo typeGoodsVo);

    /**
     * 查询单个物资
     */
    GoodsVo selectGoods(Integer id);

    /**
     * 查询种类物资
     * @param typeId
     * @return
     */
    List<GoodsVo> selectGoodsByTypeId(Integer typeId);

    /**
     * 模糊查询物资
     */
    Page<TypeGoodsVo> queryTypeGoods(String goodsName, Integer pageNum, Integer pageSize);

        /**
         * 修改物资
         */
    boolean updateGoods(Goods goods);

//    根据组织机构id获取组织所有物质种类
    List<TypeGoodsVo> getGoodsTypeByOfficeId(String officeId);
}
