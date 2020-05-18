package com.xy.cmbms.controller;


import com.xy.cmbms.base.ResponseVo;
import com.xy.cmbms.constant.BaseConstant;
import com.xy.cmbms.entity.dos.User;
import com.xy.cmbms.entity.vos.OfficeVo;
import com.xy.cmbms.entity.vos.UserVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.exception.BusinessException;
import com.xy.cmbms.service.OfficeService;
import com.xy.cmbms.service.UserService;
import com.xy.cmbms.util.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:39
 */
@Api(value = "用户信息", description = "账号设置逻辑")
@RestController
@RequestMapping("api/userController")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private OfficeService officeService;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseVo login(HttpServletRequest request, HttpServletResponse response, @RequestParam("phone") String phone, @RequestParam("passWord") String passWord) throws IOException, ServletException {


        Map<String, Object> map = new HashMap<>();
        // 编码
        System.out.println(request);
        request.setCharacterEncoding("utf-8");

        //开始对用户进行校验
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(passWord)) {
            throw new BusinessException(ErrorEnum.USER_ACCOUNT_UNKNOWN);
        }
        String encryPwd = MD5Util.getMd5(passWord);
        UserVo user = userService.getUserByLogin(phone, encryPwd);

        if (userService.isExist(phone, encryPwd) != BaseConstant.ZERO) {
            if (user != null) {
                //生成token令牌
//                String token = UUID.randomUUID().toString().replaceAll("-", "");
                //存入redis
//                redisTemplate.opsForValue().set(token,user, Duration.ofMinutes(30L));

//                return new ResponseVo(ErrorEnum.LOGIN_SUCCESS, token);
                return new ResponseVo(ErrorEnum.SUCCESS, user);
            }
        }
        request.setAttribute("error", "用户名或密码错误!");
        return new ResponseVo(ErrorEnum.LOGIN_FAILD);

    }

    @ApiOperation(value = "用户退出", notes = "用户退出")
    @RequestMapping(value = "/outLogin", method = RequestMethod.GET)
    public ResponseVo outLogin(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader("token");
        Boolean isDelete = redisTemplate.delete(token);
        if (isDelete == false) {
            return new ResponseVo(ErrorEnum.LOGINOUT_FAILED);
        }
        return new ResponseVo(ErrorEnum.LOGINOUT_SUCCESS);
    }


    /**
     * 注册个人用户
     *
     * @param request
     * @param response
     * @param userVo
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public ResponseVo regist(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVo userVo) {

        if (StringUtils.isEmpty(userVo.getUserName())) {
            return new ResponseVo(ErrorEnum.NAME_NOT_NULL);
        }
        if (StringUtils.isEmpty(userVo.getPassWord())) {
            return new ResponseVo(ErrorEnum.PASSWORD_NOT_NULL);
        }
        if (StringUtils.isEmpty(userVo.getUserType())) {
            return new ResponseVo(ErrorEnum.USERTYPE_NOT_NULL);
        }
//        if (StringUtils.isEmpty(userVo.getIdentityNumber())) {
//            return new ResponseVo(ErrorEnum.IDENTITYNUMBER_BOT_NULL);
//        }
        if (StringUtils.isEmpty(userVo.getPhone())) {
            return new ResponseVo(ErrorEnum.PHONTNUMBER_ERROR);
        }

        userVo.setCreateDate(new Date());

        int ret = userService.Regist(userVo);

        return new ResponseVo(ErrorEnum.SUCCESS, ret);
    }

    /**
     * 修改个人用户
     *
     * @param userVo
     * @return
     */
    @ApiOperation(value = "用户个人信息修改", notes = "用户个人信息修改")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public ResponseVo updateUserInfo(@RequestBody UserVo userVo) {
        int ret = userService.updateUserInfo(userVo);
        return new ResponseVo(ErrorEnum.SUCCESS, ret);
    }


    /**
     * 通过token获取用户信息
     *
     * @return
     */
    @ApiOperation(value = "通过token获取用户信息", notes = "通过token获取用户信息")
    @RequestMapping(value = "/getLoginUser", method = RequestMethod.GET)
    public ResponseVo getLoginUser(HttpServletRequest request) {

        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if (user != null) {
            System.out.println(user);
            return new ResponseVo(ErrorEnum.SUCCESS, user);
        }
        return new ResponseVo(ErrorEnum.LOGIN_USER_FAILED);
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public ResponseVo deleteUser(@RequestParam("userId") Integer userId) {
        userService.deleteUser(userId);
        return new ResponseVo(ErrorEnum.SUCCESS);
    }

    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
    public ResponseVo getAllUser() {
        List<User> list = userService.getAllUser();
        return new ResponseVo(ErrorEnum.SUCCESS, list);
    }


    public Map<String, String> productToken(String key, String value) {
        Map<String, String> infoMap = new HashMap<>();
        if (redisTemplate.opsForValue().get(key) == null) {
            //将登陆的信息保存如redis
            redisTemplate.opsForValue().set(key, value);
            infoMap.put(key, value);
        }
        //设置token有效的时间
        redisTemplate.expire(key, 3000, TimeUnit.SECONDS);
        return infoMap;
    }

}
