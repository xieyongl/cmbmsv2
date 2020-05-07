package com.xy.cmbms.controller;


import com.xy.cmbms.base.ResponseVo;
import com.xy.cmbms.entity.vos.BorVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.service.BorinfoService;
import com.xy.cmbms.service.BorrowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/borrowController")
public class BorrowController {

    @Resource
    private BorrowService borrowService;

    @Resource
    private BorinfoService borinfoService;

    @ApiOperation(value = "预定物资生成订单", notes = "预定物资生成订单")
    @RequestMapping(value = "/reserveGoods", method = RequestMethod.POST)
    public ResponseVo reserveGoods(@RequestBody BorVo borVo) {
        int ret = borrowService.reserveGoods(borVo);
        return new ResponseVo(ErrorEnum.SUCCESS,ret);
    }


    @ApiOperation(value = "获取待审核物资借用订单", notes = "获取待审核物资借用订单")
    @RequestMapping(value = "/getAduitOrder", method = RequestMethod.POST)
    public ResponseVo getAduitOrder() {
        List<BorVo> list = borinfoService.getAduitOrder();
        return new ResponseVo(ErrorEnum.SUCCESS,list);
    }

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
        int ret = borrowService.borrowGoods(userId, flag, orderId);
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
}
