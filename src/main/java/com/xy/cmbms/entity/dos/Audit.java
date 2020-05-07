package com.xy.cmbms.entity.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xieyong
 * @date 2019/12/15 - 14:20
 */
@TableName("tbl_audit")
@Data
public class Audit implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; //
    private Integer userId;
    private Integer officeId;
    private Integer msgId;
    private Integer auditResults;
    private String auditOpinion;
    private Integer auditBy;
    private Date auditTime;
    private Integer createBy;
    private Date createTime;
    private Integer delFlag;
}
