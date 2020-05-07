package com.xy.cmbms.entity.vos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xieyong
 * @date 2019/12/18 - 19:03
 */
@Data
public class GoodsVo implements Serializable{

    private Integer id; //物资id
    private Integer typeId; //物资类别id
    private Integer officeId;  //物资所属组织id
    private String officeName;  //物资所属组织名称
    private String typeName;    //物资类别名称

    private String goodsPic;    //物资类别图
    private Integer goodsTotal;    //此类别物资总数（当前组织）
    private Integer goodsRemain;   //剩余可借数
    private Integer createBy;  //物资创建人
    private Date createDate;   //物资创建时间
    private Integer updateBy;  //物资更改人
    private Date updateDate;   //物资更改时间
    private String remarks; //备注
    private Integer goodsStatus;   //物资当前状态：0.可借，1.不可借
    private Integer delFlag;   //删除标记：1：正常，0：注销

    private Integer userId;
}
