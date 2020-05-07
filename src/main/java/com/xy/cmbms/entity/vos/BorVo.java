package com.xy.cmbms.entity.vos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Xieyong
 * @date 2020/2/24 - 14:37
 */
@Data
public class BorVo implements Serializable{

    private Integer id; //id
    private Integer borrowParty;   //物资借用方id
    private Integer targetOffice;  //被借用结构组织id
    private List<BorrowVo> borrowList; //申请借用信息
    private Date borrowTime;   //借用时间
    private Date returnTime;   //归还时间
    private Integer borrowOpinion; //是否同意出借：null:待审核 1.同意，0.不同意
    private Integer auditBy; //审核人
    private String usefor;  //用于干什么
    private Integer delFlag; //删除标记：1：正常，0：注销',



//    private Integer id; //借用申请表id
//    private List<Borrow> borrowList; //申请借用信息
//    private Integer typeId;   //物资类别id
//    private Integer borrowNumber;  //借用数量
//    private Integer borrowOpinion; //是否同意出借：null:待审核 1.同意，0.不同意
//    private Integer type;   //借用类型：1.预借(还未借出)，2.已借（待还）3.已还（作废）
//    private String borrowAddress;  //出借物资地点
//    private String returnAddress;  //归还物资地点(可以默认为出借物资地点)
//    private Integer del_flag; //删除标记：1：正常，0：注销',

    private Integer userId;
    private String userName;
    private Integer officeId;
    private String officeName;
}
