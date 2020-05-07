package com.xy.cmbms.entity.dtos;

import lombok.Data;

import java.util.Date;

/**
 * @author Xieyong
 * @date 2019/12/18 - 18:48
 */
@Data
public class GoodsDto {
    private Integer id; //物资id
    private Integer TypeId;    //物资类型id
    private Integer officeId;  //物资所属组织id
    private String typeName;  //物资名称
    private String goodsPic;   //物资图片
    private Integer goodsTotal;    //物资总数
    private Integer goodsRemain;   //剩余可借数
    private Integer createBy;  //物资创建人
    private Date createDate;   //物资创建时间
    private Integer updateBy;  //物资更改人
    private Date updateDate;   //物资更改时间
    private String remarks; //备注
    private Integer goodsStatus;   //物资当前状态：1.可借，2.不可借
    private Integer delFlag;   //删除标记：1：正常，0：注销
}
