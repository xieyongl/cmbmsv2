package com.xy.cmbms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cmbms.constant.BaseConstant;
import com.xy.cmbms.entity.dos.User;
import com.xy.cmbms.entity.vos.UserVo;
import com.xy.cmbms.mapper.UserMapper;
import com.xy.cmbms.service.UserService;
import com.xy.cmbms.util.MD5Util;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:51
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private DozerBeanMapper dozerBeanMapper;


    public User getUserByLogin(String userName, String encryPwd) {
        return userMapper.getUserByLogin(userName, encryPwd);
    }


    @Override
    public int isExist(String userName, String pwd) {
        return userMapper.isExist(userName, pwd);
    }


    @Override
    public Integer Regist(UserVo userVo) {

        String tempPwd = MD5Util.getMd5(userVo.getPassWord());
        User user = new User();
        dozerBeanMapper.map(userVo,user);

        user.setPassWord(tempPwd);
        user.setDelFlag(BaseConstant.ONE);
        userMapper.insert(user);

        Integer userId = user.getUserId();
        return userId;
    }

    public int updateUserInfo(UserVo userVo) {

        userVo.setUpdateBy(userVo.getUserId());
        userVo.setUpdateDate(new Date());

        User user = new User();
        dozerBeanMapper.map(userVo,user);
        userMapper.updateById(user);
        return 1;
    }


}
