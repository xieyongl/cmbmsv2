package com.xy.cmbms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.User;
import com.xy.cmbms.entity.vos.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:55
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    UserVo getUserByLogin(@Param("phone") String phone, @Param("enctyPwd") String encryPwd);

    /**
     * 根据用户名(密码)判断当前用户是否存在
     */
    Integer isExist(@Param("phone") String phone, @Param("passWord") String passWord);

    List<User> getAllUser();

    void deleteUser(@Param("userId") Integer userId);

    //判断当前用户是否是管理员
    UserVo isAdmin(@Param("userId") Integer userId);

    //用户关联到机构
    void relateToOffice(@Param("userId") int userId, @Param("officeId") int officeId);

    //更改用户类型
    void updateUserType(@Param("userId") int userId, @Param("userType") int userType);
}