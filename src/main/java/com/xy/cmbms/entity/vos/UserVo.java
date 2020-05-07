package com.xy.cmbms.entity.vos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:58
 */
@Data
public class UserVo implements Serializable {

    private Integer UserId; //用户Id

    private Integer officeId;  //组织Id(如果是个人用户，不需要，如果是机构组织的用户账号，需要)
    private String loginName;   //登录名

    private String passWord;    //用户密码

    private String userName;    //用户名

    private Integer userType;   //用户类型：1.普通学生，2.教师，3.学校其他工作人员，4.部门账号（社团，团体，班集体）

    private String identityNumber; //身份认证号：1.学号，2.教师号，3.工作人员识别号（非学生教师的人），4.部门号（如团支部）

    private String phone;  //电话

    private String qq; //QQ号
    private String email;   //邮箱
    private String userPic; //用户头像
    private Date loginDate;     //最新登录时间
    private Integer createBy;    //用户创建者
    private Date createDate;    //用户创建时间
    private Integer updateBy;    //用户更改者
    private Date updateDate;    //用户更改时间
    private String remarks;     //备注
    private Integer delFlag;    //删除标记：1：正常用户，0：注销用户
}