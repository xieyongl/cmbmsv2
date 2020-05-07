package com.xy.cmbms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:55
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User getUserByLogin(@Param("userName") String userName, @Param("enctyPwd") String encryPwd);

        /**
         * 根据用户名(密码)判断当前用户是否存在
         */
     Integer isExist(@Param("userName") String userName, @Param("passWord") String passWord);

     List<User> getAllUser();

}
