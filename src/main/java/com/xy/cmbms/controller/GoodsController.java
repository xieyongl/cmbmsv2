package com.xy.cmbms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.cmbms.base.ResponseVo;
import com.xy.cmbms.entity.vos.GoodsVo;
import com.xy.cmbms.entity.vos.TypeGoodsVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:45
 */
@Api(value = "物资管理", description = "物资管理")
@RestController
@RequestMapping("/goodsController")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 增加物资类别
     * @param typeGoodsVo
     * @return
     */
    @ApiOperation(value = "增加物资类别", notes = "增加物资类别")
    @RequestMapping(value = "/addGoodsType", method = RequestMethod.POST)
    public ResponseVo addGoodsType(@RequestBody TypeGoodsVo typeGoodsVo) {
        int typeId = goodsService.addGoodsType(typeGoodsVo);
        return new ResponseVo(ErrorEnum.SUCCESS,typeId);
    }

    /**
     * 增加物资数量
     * @param goodsVo
     * @return
     */
//    @ApiOperation(value = "增加物资数量", notes = "增加物资数量")
//    @RequestMapping(value = "/addGoodsType", method = RequestMethod.POST)
//    public ResponseVo addGoods(@RequestBody GoodsVo goodsVo) {
//        int ret =
//        return new ResponseVo(ErrorEnum.SUCCESS,ret);
//    }

    /**
     * 删除物资 (单个)
     * @param id 物资id
     */
    @ApiOperation(value = "删除物资 (单个)", notes = "删除物资 (单个)")
    @RequestMapping(value = "/deleteGoods", method = RequestMethod.POST)
    public ResponseVo deleteGoods(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "id") Integer id) {
        return new ResponseVo(ErrorEnum.SUCCESS, goodsService.deleteGoods(userId, id));
    }

    /**
     * 删除物资类别
     * @param typeGoodsVo
     * @return
     */
    @ApiOperation(value = "删除物资类别", notes = "删除物资类别")
    @RequestMapping(value = "/deleteTypeGoods", method = RequestMethod.POST)
    public ResponseVo deleteTypeGoods(@RequestBody TypeGoodsVo typeGoodsVo) {
        return new ResponseVo(ErrorEnum.SUCCESS, goodsService.deleteTypeGoods(typeGoodsVo));
    }

    /**
     * 根据物资id查询单个物资
     * 在不知道物资所属机构组织的情况下可以通过id查询
     *
     */
    @ApiOperation(value = "查询单个物资", notes = "查询单个物资")
    @RequestMapping(value = "/selectGoods", method = RequestMethod.GET)
    public ResponseVo selectGoods(Integer id) {
        return new ResponseVo(ErrorEnum.SUCCESS, goodsService.selectGoods(id));
    }

    /**
     * 根据物资种类id查询该组织单个种类所有物资
     *
     * @param typeId 物资种类id
     */
    @ApiOperation(value = "查询种类物资", notes = "查询种类物资")
    @RequestMapping(value = "/selectTypeGoods", method = RequestMethod.GET)
    public ResponseVo selectGoodsByTypeId(@RequestParam(value = "typeId") int typeId) {
        List<GoodsVo> goodsList = goodsService.selectGoodsByTypeId(typeId);
        return new ResponseVo(ErrorEnum.SUCCESS,goodsList);
    }

    /**
     * 根据组织机构id获取组织所有物质种类
     */
    @ApiOperation(value = "获取组织的所有物资种类信息", notes = "获取组织的所有物资种类信息")
    @RequestMapping(value = "/queryGoods", method = RequestMethod.GET)
    public ResponseVo getGoodsTypeByOfficeId(String officeId) {
        List<TypeGoodsVo> list = goodsService.getGoodsTypeByOfficeId(officeId);
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
        Page<TypeGoodsVo> page = goodsService.queryTypeGoods(goodsName, pageNum, pageSize);
        return new ResponseVo(ErrorEnum.SUCCESS,page);
    }

}
