package com.xy.cmbms.controller;


import com.xy.cmbms.base.ResponseVo;
import com.xy.cmbms.entity.vos.OfficeVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.service.OfficeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Xieyong
 * @date 2020/1/14 - 17:09
 */
@Api(tags = "机构组织操作", description = "机构组织操作")
@RestController
@RequestMapping("officeController")
public class OfficeController {

    @Resource
    OfficeService officeService;

    @ApiOperation(value = "组织机构注册", notes = "组织机构注册")
    @RequestMapping(value = "/registOffice", method = RequestMethod.POST)
    public ResponseVo RegistOffice(@RequestBody OfficeVo officeVo) {
        if (StringUtils.isEmpty(officeVo.getName())) {
            return new ResponseVo(ErrorEnum.OFFICE_NAME_ISNULL);
        }
        if (StringUtils.isEmpty(officeVo.getType())) {
            return new ResponseVo(ErrorEnum.OFFICE_TYPE_ISNULL);
        }
        if (StringUtils.isEmpty(officeVo.getPrimaryPerson())) {
            return new ResponseVo(ErrorEnum.OFFICE_PRIMARY_PERSON_ISNULL);
        }
        if (StringUtils.isEmpty(officeVo.getPhone())) {
            return new ResponseVo(ErrorEnum.OFFICE_PHONE_ISNULL);
        }
        if (StringUtils.isEmpty(officeVo.getEmail())) {
            return new ResponseVo(ErrorEnum.OFFICE_EMAIL_ISNULL);
        }

        int officeId = officeService.RegistOffice(officeVo);
        return new ResponseVo(ErrorEnum.SUCCESS,officeId);
    }

//    @ApiOperation(value = "组织机构修改", notes = "组织机构修改")
//    @RequestMapping(value = "/updateOffice", method = RequestMethod.POST)
//    public ResponseVo UpdateOffice(@RequestBody OfficeVo officeVo) {
//
//        return ResponseVo(ErrorEnum.SUCCESS,);
//    }
}
