package com.xy.cmbms.controller;


import com.xy.cmbms.base.ResponseVo;
import com.xy.cmbms.entity.vos.MessageVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:44
 */
@Api(value = "消息资讯", description = "消息资讯")
@RestController
@RequestMapping("api/messageController")
@CrossOrigin
public class MessageController {

    @Resource
    private MessageService messageService;

    @ApiOperation(value = "发布新鲜资讯", notes = "发布新鲜资讯")
    @RequestMapping(value = "/saveMsg", method = RequestMethod.POST)
    public ResponseVo saveMsg(@RequestBody MessageVo messageVo) {
        int ret = messageService.saveMsg(messageVo);
        return new ResponseVo(ErrorEnum.SUCCESS,ret);
    }


    @ApiOperation(value = "待办消息显示", notes = "待办消息显示")
    @RequestMapping(value = "/toAuditMsg", method = RequestMethod.GET)
    public ResponseVo toAuditMsg() {
        List<MessageVo> list = messageService.toAuditMsg();
        return new ResponseVo(ErrorEnum.SUCCESS,list);
    }


    @ApiOperation(value = "查看消息详情", notes = "查看消息详情")
    @RequestMapping(value = "/getMessage", method = RequestMethod.POST)
    public ResponseVo getMessage(@RequestParam(value = "msgId") Integer msgId) {
        MessageVo messageVo = messageService.getMessage(msgId);
        return new ResponseVo(ErrorEnum.SUCCESS,messageVo);
    }

    /**
     *
     * @param msgId
     * @param auditOpinion 1同意 0 不同意
     * @param userId
     * @return
     */
    @ApiOperation(value = "审核资讯消息", notes = "审核资讯消息")
    @RequestMapping(value = "/auditMsg", method = RequestMethod.POST)
    public ResponseVo auditMsg(@RequestParam(value="msgId") Integer msgId,
                               @RequestParam(value="auditOpinion") int auditOpinion,
                               @RequestParam(value="userId") Integer userId) {
        int ret = messageService.auditMsg(msgId, auditOpinion, userId);
        return new ResponseVo(ErrorEnum.SUCCESS,ret);
    }

    @ApiImplicitParam(name="flag",value="1.所有消息 2.待审核消息 3.审核通过的消息 4.审核不通过的消息",dataType="int", paramType = "query")
    @ApiOperation(value = "获取消息资讯", notes = "获取消息资讯")
    @RequestMapping(value = "/getMessageList", method = RequestMethod.GET)
    public ResponseVo getMessageList(int flag) {
        List<MessageVo> list = messageService.getMessageList(flag);
        return new ResponseVo(ErrorEnum.SUCCESS,list);
    }



}
