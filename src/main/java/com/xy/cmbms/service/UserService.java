package com.xy.cmbms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.cmbms.entity.dos.User;
import com.xy.cmbms.entity.vos.UserVo;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:51
 */
public interface UserService extends IService<User> {

    User getUserByLogin(String userName, String encryPwd);

    int isExist(String userName, String pwd);

    Integer Regist(UserVo userVo);

    int updateUserInfo(UserVo userVo);

}
