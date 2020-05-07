package com.xy.cmbms.entity.vos;

import lombok.Data;

import java.util.Date;

/**
 * @author Xieyong
 * @date 2020/3/15 - 12:11
 */
@Data
public class BackGoods {
    private Integer borinfoId; //id
    private Integer borrowParty;   //物资借用方id
    private Integer targetOffice;  //被借用结构组织id
    private Date borrowTime;   //借用时间
    private Date returnTime;   //归还时间
    private Integer borrowOpinion; //是否同意出借：null:待审核 1.同意，0.不同意
    private Integer auditBy; //审核人
    private String usefor;  //用于干什么
    private Integer borinfoFlag; //删除标记：1：正常，0：注销',

//    private Integer borrowId; //物资借用申请表id
////    private Integer borinfoId; //申请借用信息表id
//    private Integer typeId;   //物资类别id
//    private Integer borrowNumber;  //借用数量
//    private Integer type;   //借用类型：1.预借(还未借出)，2.已借（待还）3.已还（作废）
//    private String borrowAddress;  //出借物资地点
//    private String returnAddress;  //归还物资地点(可以默认为出借物资地点)
//    private Integer borrowFlag; //删除标记：1：正常，0：注销',
}
