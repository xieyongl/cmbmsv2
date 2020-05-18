package com.xy.cmbms.entity.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Xieyong
 * @date 2020/5/15 - 18:45
 */
@Data
public class AuditOfficeDto implements Serializable {
    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("审核人id")
    private Integer auditUserId; //审核人id（管理员id）

    @ApiModelProperty("组织id")
    private Integer officeId; //组织Id

    @ApiModelProperty("审核意见")
    private int auditOpinion; //2.同意， 3.不同意
}
