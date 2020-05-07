package com.xy.cmbms.entity.vos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xieyong
 * @date 2020/2/14 - 13:13
 */
@Data
public class MessageVo implements Serializable{
    private Integer userId;
    private Integer officeId;
    private Integer id; //消息id
    private Integer msgType;   //消息类型：1.公告，2.校园资讯，3.寻物启事，4.其他
    private String title;   //消息题目
    private String content; //消息内容
    private String pic; //消息配图路径
    private Integer createBy;  //消息创建人Id
    private Date createTime;   //消息创建时间
    private Date publishTime;   //消息发布时间
    private int publish;   //消息是否发布  1.发布  0.未发布
    private Integer delFlag;   //删除标记  1.正常  0.过期

    private Integer auditResults;
    private Integer auditOpinion;
    private Integer auditBy;
    private Date auditTime;
    private String userName;
    private String officeName;
}
