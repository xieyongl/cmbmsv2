package com.xy.cmbms.entity.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xieyong
 * @date 2019/12/15 - 14:14
 */
@TableName("tbl_message")
@Data
public class Message implements Serializable{

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; //消息id
    private Integer msgType;   //消息类型：1.公告，2.校园资讯，3.寻物启事，4.其他
    private String title;   //消息题目
    private String content; //消息内容
    private String pic; //消息配图路径
    private Integer createBy;  //消息创建人Id
    private Date createTime;   //消息创建时间
    private Date publishTime;   //消息发布时间
    private Integer publish;   //发布  0.待审核发布  1.已发布
    private Integer del_flag;   //删除标记  1.正常  0.过期
}
