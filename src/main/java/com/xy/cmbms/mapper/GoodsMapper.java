package com.xy.cmbms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.Goods;
import com.xy.cmbms.entity.dtos.GoodsDto;
import com.xy.cmbms.entity.vos.TypeGoodsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/18 - 18:34
 */
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 增加物资
     */
    int addGoods(Goods goods);

    /**
     * 删除物资(单个)
     */
    void deleteGoods(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId, @Param("updateDate") Date updateDate);

    //删除同类所有物资
    void deleteTypeAllGoods(@Param("userId") Integer userId, @Param("typeId") Integer id, @Param("updateDate") Date updateDate);


    /**
     * 查询组织物资
     */
    List<GoodsDto> selectGoodsByTypeId(@Param("typeId") Integer typeId);

    /**
     * 修改物资
     */
    boolean updateGoods(Goods goods);


    List<TypeGoodsVo> getGoodsTypeByOfficeId(@Param("officeId") String officeId);
}
