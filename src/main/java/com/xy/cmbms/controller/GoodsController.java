package com.xy.cmbms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.cmbms.base.ResponseVo;
import com.xy.cmbms.constant.BaseConstant;
import com.xy.cmbms.entity.dos.Goods;
import com.xy.cmbms.entity.dos.TypeGoods;
import com.xy.cmbms.entity.vos.GoodsVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:45
 */
@Api(value = "物资管理", description = "物资管理")
@RestController
@RequestMapping("api/goodsController")
@CrossOrigin
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 增加物资类别(球类，桌椅类)
     * @param goodsVo
     * @return
     */
    @ApiOperation(value = "增加物资类别(球类，桌椅类)", notes = "增加物资类别(球类，桌椅类)")
    @RequestMapping(value = "/addGoodsType", method = RequestMethod.POST)
    public ResponseVo addGoodsType(@RequestBody GoodsVo goodsVo) {
        int typeId = goodsService.addGoodsType(goodsVo);
        return new ResponseVo(ErrorEnum.SUCCESS,typeId);
    }

    /**
     * 获取物资类别(球类，桌椅类)
     */
    @ApiOperation(value = "获取物资类别(球类，桌椅类)", notes = "获取物资类别(球类，桌椅类)")
    @RequestMapping(value = "/getGoodsTypeList", method = RequestMethod.POST)
    public ResponseVo getGoodsTypeList() {
        List<TypeGoods> list = goodsService.getGoodsTypeList();
        return new ResponseVo(ErrorEnum.SUCCESS,list);
    }



    /**
     * 增加物资名称及数量(篮球 足球)
     * @param goodsVo
     * @return
     */
    @ApiOperation(value = "增加物资名称及数量(篮球 足球)", notes = "增加物资名称及数量(篮球 足球)")
    @RequestMapping(value = "/addGoods", method = RequestMethod.POST)
    public ResponseVo addGoods(@RequestBody GoodsVo goodsVo) {
        int typeId = goodsService.addGoods(goodsVo);
        return new ResponseVo(ErrorEnum.SUCCESS,typeId);
    }

    /**
     *
     * @param flag 1.增加物资数量 2.减少物资数量
     * @param goodsId
     * @param updateNum 数量
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="flag",value="1.增加物资数量 2.减少物资数量",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="goodsId",value="物资id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="updateNum",value="增加/减少物资数量",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="goodsStatus",value="物资状态：0.不可借， 1.可借 ",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="goodsType",value="物资大类类型id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="userId",value="操作用户id",dataType="Integer", paramType = "query")})
    @ApiOperation(value = "更改物资", notes = "更改物资")
    @RequestMapping(value = "/updateGoods", method = RequestMethod.POST)
    public ResponseVo updateGoods(@RequestParam(value = "flag", required = false) Integer flag,
                                     @RequestParam(value = "goodsId", required = true) Integer goodsId,
                                     @RequestParam(value = "updateNum", required = false) Integer updateNum,
                                     @RequestParam(value = "goodsStatus", defaultValue = "1") Integer goodsStatus,
                                     @RequestParam(value = "goodsType", required = true) Integer goodsType,
                                     @RequestParam(value = "userId", required = true) Integer userId) {
        goodsService.updateGoods(flag, goodsId, updateNum, userId, goodsStatus, goodsType);
        return new ResponseVo(ErrorEnum.SUCCESS);
    }


    /**
     * 删除物资类型（篮球 足球）
     * @param
     * @return
     */
    @ApiOperation(value = "删除物资类型（篮球 足球）", notes = "删除物资类型（篮球 足球）")
    @PostMapping(value = "/deleteTypeGoods")
    public ResponseVo deleteTypeGoods(@RequestParam(value = "userId") Integer userId,
                                      @RequestParam(value = "officeId") Integer officeId,
                                      @RequestParam(value = "id") Integer id) {
        return new ResponseVo(ErrorEnum.SUCCESS, goodsService.deleteGoods(userId, officeId, id));
    }

    /**
     * 删除物资大类（球类）
     * @param
     * @return
     */
    @ApiOperation(value = "删除物资大类（球类）", notes = "删除物资大类（球类）")
    @PostMapping(value = "/deleteGoodsType")
    public ResponseVo deleteGoodsType(@RequestParam(value = "userId") Integer userId,
                                      @RequestParam(value = "id") Integer id) {
        int temp = goodsService.deleteGoodsType(userId, id);
        if (temp == 0) {
            return new ResponseVo(ErrorEnum.SUCCESS, BaseConstant.USER_ISNOT_ADMIN);
        }
        return new ResponseVo(ErrorEnum.SUCCESS, temp);
    }

    /**
     * 根据组织机构id获取组织所有物质种类
     */
    @ApiOperation(value = "获取组织的所有物资种类信息（足球，篮球）", notes = "获取组织的所有物资种类信息（足球，篮球）")
    @RequestMapping(value = "/queryGoods", method = RequestMethod.GET)
    public ResponseVo getGoodsTypeByOfficeId(@RequestParam("officeId") String officeId) {
        List<GoodsVo> list = goodsService.getGoodsByOfficeId(officeId);
        return new ResponseVo(ErrorEnum.SUCCESS,list);
    }

    /**
     * 首界面模糊查询需要借用的物资
     */
    @ApiOperation(value = "模糊查询种类物资", notes = "模糊查询种类物资")
    @RequestMapping(value = "/queryTypeGoods", method = RequestMethod.GET)
    public ResponseVo queryTypeGoods(@RequestParam(value="goodsName") String goodsName,
                                     @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize) {
        Page<GoodsVo> page = goodsService.queryTypeGoods(goodsName, pageNum, pageSize);
        return new ResponseVo(ErrorEnum.SUCCESS,page);
    }

    /**
     * 获取所有机构所有物资信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="officeId",value="机构id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="goodsTypeId",value="物资类型id",dataType="Integer", paramType = "query")})
    @ApiOperation(value = "获取物资信息", notes = "获取物资信息")
    @RequestMapping(value = "/getAllGoods", method = RequestMethod.GET)
    public ResponseVo getAllGoods(@RequestParam(value = "officeId",required = false) Integer officeId,
                                  @RequestParam(value = "goodsTypeId", required = false) Integer goodsTypeId) {
        List<GoodsVo> list = goodsService.getAllGoods(officeId, goodsTypeId);
        return new ResponseVo(ErrorEnum.SUCCESS,list);
    }

}
