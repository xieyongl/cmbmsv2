package com.xy.cmbms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cmbms.constant.BaseConstant;
import com.xy.cmbms.entity.dos.User;
import com.xy.cmbms.entity.vos.UserVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.exception.BusinessException;
import com.xy.cmbms.mapper.UserMapper;
import com.xy.cmbms.service.UserService;
import com.xy.cmbms.util.MD5Util;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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


    public UserVo getUserByLogin(String phone, String encryPwd) {
        return userMapper.getUserByLogin(phone, encryPwd);
    }


    @Override
    public int isExist(String phone, String pwd) {
        return userMapper.isExist(phone, pwd);
    }


    @Override
    public Integer Regist(UserVo userVo) {
        String phone = userVo.getPhone();
        //判断该号码是否已经注册
        int n = userMapper.isExist(phone, null);
        if (n>0) {
            throw new BusinessException(ErrorEnum.PHONE_ISREGIST);
        }
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

//        String phone = userVo.getPhone();
//        String name = userVo.getUserName();
//        String qq = userVo.getQq();
//        String email = userVo.getEmail();
        if(StringUtils.isEmpty(userVo.getPassWord())) {
            throw new BusinessException(ErrorEnum.PASSWORD_NOT_NULL);
        }
        if(StringUtils.isEmpty(userVo.getPhone())) {
            throw new BusinessException(ErrorEnum.PHONTNUMBER_ISNULL);
        }
        String tempPwd = MD5Util.getMd5(userVo.getPassWord());
        Date updateTime = new Date();
        userVo.setUpdateDate(updateTime);
        userVo.setPassWord(tempPwd);

        User user = new User();
        dozerBeanMapper.map(userVo, user);
        userMapper.updateById(user);
        return 1;
    }

    public void deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
    }

    public List<User> getAllUser() {
        List<User> list = userMapper.getAllUser();
        return list;
    }



}
