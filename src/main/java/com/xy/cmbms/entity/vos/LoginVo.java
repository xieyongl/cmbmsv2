package com.xy.cmbms.entity.vos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Xieyong
 * @date 2019/12/16 - 13:02
 */
@Data
public class LoginVo implements Serializable{

    private String loginName;
    private String passWord;
    private String captcha;
}
