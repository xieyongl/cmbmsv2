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
    private String userName;
    private Integer targetOffice;  //被借用结构组织id
    private String officeName;
    private List<BorrowVo> borrowList; //申请借用信息
    private Date borrowTime;   //借用时间
    private Date returnTime;   //归还时间
    private Date actualReturntime; //实际归还时间
    private Integer borrowOpinion; //是否同意出借：null:待审核 1.同意，0.不同意
    private Integer auditBy; //审核人
    private int borrowType; //订单借用状态： 1.预借成功，还没借用；2.借用中；3已归还
    private String usefor;  //用于干什么
    private Integer delFlag; //删除标记：1：正常，0：注销',


    //    private Integer userId;
}
