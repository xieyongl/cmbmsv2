package com.xy.cmbms.entity.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物资表
 * @author Xieyong
 * @date 2019/12/15 - 13:50
 */
@TableName("tbl_goods")
@Data
public class Goods implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; //物资id
    private Integer typeId;    //物资类别id
    private Integer createBy;  //物资创建人
    private Date createDate;   //物资创建时间
    private Integer updateBy;  //物资更改人
    private Date updateDate;   //物资更改时间
    private Integer goodsStatus;   //物资当前状态：0.可借，1.不可借
    private String remarks; //备注
    private Integer delFlag;   //删除标记：1：正常，0：注销
}
