package com.xy.cmbms.controller;


import com.xy.cmbms.base.ResponseVo;
import com.xy.cmbms.entity.dtos.AuditOfficeDto;
import com.xy.cmbms.entity.vos.OfficeVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.service.OfficeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xieyong
 * @date 2020/1/14 - 17:09
 */
@Api(tags = "机构组织操作", description = "机构组织操作")
@RestController
@RequestMapping("api/officeController")
@CrossOrigin
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
//        if (StringUtils.isEmpty(officeVo.getPrimaryPerson())) {
//            return new ResponseVo(ErrorEnum.OFFICE_PRIMARY_PERSON_ISNULL);
//        }
        if (StringUtils.isEmpty(officeVo.getPhone())) {
            return new ResponseVo(ErrorEnum.OFFICE_PHONE_ISNULL);
        }
        if (StringUtils.isEmpty(officeVo.getEmail())) {
            return new ResponseVo(ErrorEnum.OFFICE_EMAIL_ISNULL);
        }

        int ret = officeService.RegistOffice(officeVo);
        if (ret == 0) {
            return new ResponseVo(ErrorEnum.CAN_REGIST_OFFICE);
        }
        return new ResponseVo(ErrorEnum.SUCCESS,ret);
    }

//    @ApiOperation(value = "组织机构修改", notes = "组织机构修改")
//    @RequestMapping(value = "/updateOffice", method = RequestMethod.POST)
//    public ResponseVo UpdateOffice(@RequestBody OfficeVo officeVo) {
//
//        return ResponseVo(ErrorEnum.SUCCESS,);
//    }

    @ApiImplicitParam(name="flag",value="1.获取待审核机构; 2.查询所有机构(已审核通过，能够正常使用)",dataType="int", paramType = "query")
    @ApiOperation(value = "获取所有机构", notes = "获取所有机构")
    @RequestMapping(value = "/getAllOffice", method = RequestMethod.POST)
    public ResponseVo getAllOffice(int flag) {
        List<OfficeVo> list = officeService.getAllOffice(flag);
        return new ResponseVo(ErrorEnum.SUCCESS, list);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="auditUserId",value="审核人员id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="officeId",value="组织id ",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="auditOpinion",value="审核意见",dataType="int", paramType = "query")})
    @ApiOperation(value = "审核注册机构", notes = "审核注册机构")
    @RequestMapping(value = "/auditOffice", method = RequestMethod.POST)
    public ResponseVo auditOffice(@RequestBody AuditOfficeDto auditOfficeDto) {
        int ret = officeService.auditOffice(auditOfficeDto);
        return new ResponseVo(ErrorEnum.SUCCESS, ret);
    }

    @ApiOperation(value = "模糊查询机构", notes = "模糊查询机构")
    @RequestMapping(value = "/getOfficeByQuery", method = RequestMethod.GET)
    public ResponseVo getOfficeByQuery(@RequestParam(value = "officeName",required = true) String officeName) {
        List<OfficeVo> list = officeService.getOfficeByQuery(officeName);
        return new ResponseVo(ErrorEnum.SUCCESS, list);
    }
}
