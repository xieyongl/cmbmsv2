package com.xy.cmbms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.Goods;
import com.xy.cmbms.entity.dtos.GoodsDto;
import com.xy.cmbms.entity.vos.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

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
     * 修改物资
     */
    boolean updateGoods(Goods goods);


    List<GoodsVo> getGoodsByOfficeId(@Param("officeId") String officeId);

    /**
     * 获取所有机构所有物资信息
     */
    List<GoodsVo> getAllGoods(@Param("officeId") Integer officeId, @Param("goodsTypeId") Integer goodsTypeId);

    /**
     *更改物资数量
     */
    void updateGoods(@Param("goodsId") Integer goodsId,
                        @Param("updateTotalNum") Integer updateTotalNum,
                        @Param("updateRemainNum") Integer updateRemainNum,
                        @Param("userId") Integer userId,
                        @Param("goodsStatus") Integer goodsStatus,
                        @Param("goodsType") Integer goodsType);
}
