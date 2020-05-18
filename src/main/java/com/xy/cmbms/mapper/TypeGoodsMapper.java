package com.xy.cmbms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.Goods;
import com.xy.cmbms.entity.dos.TypeGoods;
import com.xy.cmbms.entity.dtos.TypeGoodsDto;
import com.xy.cmbms.entity.vos.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/19 - 15:16
 */
@Repository
public interface TypeGoodsMapper extends BaseMapper<TypeGoods> {

    /**
     * 获取物资类别(球类，桌椅类)
     */
    List<TypeGoods> getGoodsTypeList();


    TypeGoods hasTypeGoods(@Param("typeName") String typeName);

    /**
     * 判断当前机构组织是否有此类别物资
     */
    Goods hasGoods(@Param("officeId") Integer officeId, @Param("goodsName") String goodsName);


    /**
     * 删除当前机构的该类别物资
     */

    void deleteGoods(@Param("userId") Integer userId,
                            @Param("updateDate") Date updateDate,
                            @Param("officeId") Integer officeId,
                            @Param("id") Integer id);

    /**
     * 删除物资大类
     */
    int deleteGoodsType(@Param("id") Integer id);

    //根据物资种类id获取该类物资信息
    List<GoodsVo> selectGoodsById(@Param("goodsId") Integer goodsId);

    /**
     * 模糊查询物资
     *
     * @param goodsName
     * @return
     */
    List<GoodsVo> queryTypeGoods(@Param("goodsName") String goodsName);


    /**
     * 预定之后更新剩余物资
     */
    void updateRemain(@Param("typeId") Integer typeId, @Param("goodsRemain") Integer goodsRemain);

    /**
     * 物资数量归位
     */
    void goodsPlace(@Param("goodsId") Integer goodsId, @Param("afterNum") Integer afterNum);
}

