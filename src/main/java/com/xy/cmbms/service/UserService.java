package com.xy.cmbms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.cmbms.base.ResponseVo;
import com.xy.cmbms.entity.dos.User;
import com.xy.cmbms.entity.vos.UserVo;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:51
 */
public interface UserService extends IService<User> {

    UserVo getUserByLogin(String phone, String encryPwd);

    int isExist(String phone, String pwd);

    Integer Regist(UserVo userVo);

    int updateUserInfo(UserVo userVo);

    void deleteUser(Integer userId);

    List<User> getAllUser();
}
