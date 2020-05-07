package com.xy.cmbms.entity.vos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Xieyong
 * @date 2020/2/25 - 14:33
 */
@Data
public class BorrowVo implements Serializable {

    private Integer id; //借用申请物资表id
    private Integer borinfoId; //申请借用信息表id
    private Integer typeId;   //物资类别id
    private Integer borrowNumber;  //借用数量
    private Integer type;   //借用类型：0.已申请待审核 1.审核通过预借(还未借出)，2.已借（待还）3.已还（作废）
    private String borrowAddress;  //出借物资地点
    private String returnAddress;  //归还物资地点(可以默认为出借物资地点)
    private Integer delFlag; //删除标记：1：正常，0：注销',

}