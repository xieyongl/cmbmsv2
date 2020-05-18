package com.xy.cmbms.controller;


import com.xy.cmbms.base.ResponseVo;
import com.xy.cmbms.entity.dos.Borinfo;
import com.xy.cmbms.entity.vos.BorVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.service.BorinfoService;
import com.xy.cmbms.service.BorrowService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:40
 */

@Api(value = "物资借用归还", description = "物资借用归还")
@RestController
@RequestMapping("api/borrowController")
@CrossOrigin
@Slf4j
public class BorrowController {

    @Resource
    private BorrowService borrowService;

    @Resource
    private BorinfoService borinfoService;


    @ApiImplicitParams({
            @ApiImplicitParam(name="borrowParty",value="借用方id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="targetOffice",value="被借用机构id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="usefor",value="借用物资用于做什么",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="goodsId",value="借用物资的id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="borrowNumber",value="借用数量",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="borrowAddress",value="借用地址",dataType="String", paramType = "query"),
            @ApiImplicitParam(name="returnAddress",value="归还地址",dataType="String", paramType = "query")})
    @ApiOperation(value = "预定物资生成订单", notes = "预定物资生成订单")
    @RequestMapping(value = "/reserveGoods", method = RequestMethod.POST)
    public ResponseVo reserveGoods(@RequestBody BorVo borVo) {
        int ret = borrowService.reserveGoods(borVo);
        return new ResponseVo(ErrorEnum.SUCCESS,ret);
    }


    @ApiOperation(value = "获取待审核物资借用订单", notes = "获取待审核物资借用订单")
    @RequestMapping(value = "/getAduitOrder", method = RequestMethod.GET)
    public ResponseVo getAduitOrder() {
        List<BorVo> list = borinfoService.getAduitOrder();
        return new ResponseVo(ErrorEnum.SUCCESS,list);
    }

    /**
     *
     * @param userId
     * @param orderId
     * @param opinion 0 不同意 1.同意
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="审核人id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="opinion",value="审核意见 0.审核不通过，不同意， 1.审核通过，同意",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="orderId",value="订单id",dataType="Integer", paramType = "query"),})
    @ApiOperation(value = "审核物资借用订单", notes = "审核物资借用订单")
    @RequestMapping(value = "/auditOrder", method = RequestMethod.POST)
    public ResponseVo auditOrder(@RequestParam("userId") Integer userId,
                                 @RequestParam("orderId") Integer orderId,
                                 @RequestParam("opinion") Integer opinion) {
        int ret = borinfoService.auditOrder(userId, orderId, opinion);
        return new ResponseVo(ErrorEnum.SUCCESS,ret);
    }

    /**
     *
     * @param userId
     * @param flag 1.成功借出, 2.取消借用
     * @param orderId
     * @return
     */
    @ApiOperation(value = "借用/预定成功之后取消借用物资", notes = "借用/预定成功之后取消借用物资")
    @RequestMapping(value = "/borrowGoods", method = RequestMethod.POST)
    public ResponseVo borrowGoods(@RequestParam("userId") Integer userId,
                                 @RequestParam("flag") Integer flag,
                                 @RequestParam("orderId") Integer orderId) {
        Map<String, Object> map = new HashMap<>();
        int ret = borrowService.isOkborrowGoods(userId, flag, orderId);
        if (ret == 1) {
            map.put("成功借出!", 1);
        } else {
            map.put("借出失效！已将物资归位！", 0);
        }
        return new ResponseVo(ErrorEnum.SUCCESS,map);
    }


    @ApiOperation(value = "归还物资", notes = "归还物资")
    @RequestMapping(value = "/returnGoods", method = RequestMethod.POST)
    public ResponseVo returnGoods(@RequestParam("userId") Integer userId,
                                 @RequestParam("orderId") Integer orderId){
        int ret = borrowService.returnGoods(userId, orderId);
        return new ResponseVo(ErrorEnum.SUCCESS,ret);
    }


    /**
     *
     * @param borrowOpinion 订单状态
     * @param borrowTime 借用时间
     * @param orderId 订单号
     * @return
     */
    @ApiImplicitParams({
    @ApiImplicitParam(name="borrowOpinion",value="订单状态（不输入（空） 待审核，1.审核通过，0.审核不通过, 2获取所有订单）",dataType="String", paramType = "query"),
    @ApiImplicitParam(name="borrowTime",value="借用时间",dataType="String", paramType = "query"),
    @ApiImplicitParam(name="orderId",value="订单号",dataType="String", paramType = "query"),
    @ApiImplicitParam(name="officeId",value="机构id",dataType="String", paramType = "query")})
    @ApiOperation(value = "获取订单信息", notes = "获取订单信息")
    @RequestMapping(value = "/getAllOrderByTerm", method = RequestMethod.GET)
    public ResponseVo getAllOrderByTerm(@RequestParam(value = "borrowOpinion", required = false) Integer borrowOpinion,
                                        @RequestParam(value = "borrowTime", required = false) String borrowTime,
                                        @RequestParam(value = "orderId", required = false) Integer orderId,
                                        @RequestParam(value = "officeId", required = false) Integer officeId){
        List<BorVo> list = borrowService.getAllOrderByTerm(borrowOpinion, borrowTime, orderId, officeId);
        return new ResponseVo(ErrorEnum.SUCCESS,list);
    }



    @ApiImplicitParam(name="orderId",value="订单id",dataType="Integer", paramType = "query")
    @ApiOperation(value = "物资成功借出，改变订单状态", notes = "物资成功借出，改变订单状态")
    @RequestMapping(value = "/borrowAfter", method = RequestMethod.POST)
    public ResponseVo borrowAfter(@RequestParam("orderId") Integer orderId){
        int ret = borrowService.borrowAfter(orderId);
        return new ResponseVo(ErrorEnum.SUCCESS, ret);
    }


    @ApiImplicitParam(name="userId",value="用户id",dataType="Integer", paramType = "query")
    @ApiOperation(value = "根据用户id获取订单信息", notes = "根据用户id获取订单信息")
    @RequestMapping(value = "/getOrderByuserId", method = RequestMethod.GET)
    public ResponseVo getOrderByuserId(@RequestParam("userId") Integer userId){
        List<BorVo> list = borrowService.getOrderByuserId(userId);
        return new ResponseVo(ErrorEnum.SUCCESS,list);
    }
}
