package com.xy.cmbms.entity.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xieyong
 * @date 2019/12/15 - 14:07
 */
@TableName("tbl_borinfo") //订单
@Data
public class Borinfo implements Serializable{

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; //id
    private Integer borrowParty;   //物资借用方id
    private Integer targetOffice;  //被借用结构组织id
    private Date borrowTime;   //借用时间
    private Date returnTime;   //归还时间
    private Integer borrowOpinion; //是否同意出借：null:待审核 1.同意，0.不同意
    private Integer auditBy; //审核人
    private String usefor;  //用于干什么
    private Integer delFlag; //删除标记：1：正常，0：注销',
}
