package com.xy.cmbms.enums;


import com.xy.cmbms.exception.BusinessException;

/**
 * @author Xieyong
 * @date 2019/11/27 - 16:26
 */
public enum ErrorEnum {

    SUCCESS(0, "success"),
    FAILED(-1, "服务错误"),
    CAN_REGIST_OFFICE(500, "非教师账号不能注册机构"),
    LOGIN_USER_FAILED(-2, "获取用户登录失败！"),
    USER_ACCOUNT_UNKNOWN(10000, "用户不存在"),
    LESS_THAN_ZERO(10005, "物资数量不能小于0"),
    NAME_NOT_NULL(10006, "用户名不能为空"),
    PASSWORD_NOT_NULL(10007, "用户密码不能为空"),
    USERTYPE_NOT_NULL(10008, "用户类型不能为空"),
    IDENTITYNUMBER_BOT_NULL(10009, "用户身份认证号不能为空"),
    PHONTNUMBER_ERROR(10010,"电话号码填写错误"),
    PHONTNUMBER_ISNULL(10011,"电话号码不能为空"),

    USER_ISNOT_ADMIN(10012, "只有管理员才有权限操作"),

    LOGIN_SUCCESS(200, "登录成功！"),
    LOGIN_FAILD(11112, "登录失败！"),
    LOGINOUT_SUCCESS(300, "退出成功！"),
    LOGINOUT_FAILED(400, "退出失败！"),

    HAS_TYPE_GOODS(20000, "该物资类别已存在"),
    NO_TYPE_GOODS(20001, "该物资类别不存在"),
    NO_THIS_GOODS(20002, "此物资不存在"),
    NO_DELETE(20009,"物资剩余数与总数不符"),
    NOT_ENOUGH(20003,"所借物资数量超过物资可借数量"),
    CANNOT_DELETE(20004, "减少的数量大于物资剩余可借数"),


    OFFICE_NAME_ISNULL(30001,"机构组织名为空"),
    OFFICE_TYPE_ISNULL(30002,"机构组织类型为空"),
    OFFICE_PRIMARY_PERSON_ISNULL(30003,"机构组织负责人为空"),
    OFFICE_PHONE_ISNULL(30004,"机构组织联系电话为空"),
    OFFICE_EMAIL_ISNULL(30005,"机构组织邮箱地址为空"),
    OFFICE_NAME_ISEXIST(30006,"机构组织名已存在"),

    PHONE_ISREGIST(40000, "该号码已经注册"),
    TITLE_NOT_NULL(40001,"消息题目不能为空"),
    CONTENT_NOT_NULL(40002,"消息内容不能为空"),
    AUDIT_IS_OK(40003,"该消息已被审核"),
    NO_MESSAGE(40004,"暂无消息"),

    RETURNTIME_IS_NULL(50001,"归还时间不能为空"),

    ORDER_NOT_RESERVE(50001, "订单处于非预定状态"),

    OFFICE_NAME_INULL(3001111,"组织名为空");




    // 错误码
    private int errorCode;

    // 错误信息
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ErrorEnum(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    ErrorEnum(BusinessException e) {
        this.errorCode = e.getErrorCode();
        this.message = e.getMessage();
    }
}
