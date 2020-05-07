package com.xy.cmbms.entity.vos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xieyong
 * @date 2019/12/19 - 14:37
 */
@Data
public class TypeGoodsVo implements Serializable{
    private Integer id; //物资类别id
    private Integer officeId;   //组织Id
    private String typeName;    //物资类别名称
    private String goodsPic;    //物资类别图
    private Integer goodsTotal; //此类别物资总数
    private Integer goodsRemain;    //此类别物资剩余可借数
    private Integer createBy;  //此类别物资创建人
    private Date createDate;   //此类别物资创建时间
    private Integer updateBy;  //此类别物资更改人
    private Date updateDate;   //此类别物资更改时间
    private int status;    //此类别物资当前状态：0.可借，1.不可借
    private String remarks; //备注
    private Integer delFlag;   //删除标记：1：正常，0：注销

    private Integer userId;


}
