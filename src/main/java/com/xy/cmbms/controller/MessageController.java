package com.xy.cmbms.controller;


import com.xy.cmbms.base.ResponseVo;
import com.xy.cmbms.entity.vos.MessageVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.service.MessageService;
import io.swagger.annotations.Api;
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
@RequestMapping("/messageController")
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


    @ApiOperation(value = "审核资讯消息", notes = "审核资讯消息")
    @RequestMapping(value = "/auditMsg", method = RequestMethod.POST)
    public ResponseVo auditMsg(@RequestParam(value="msgId") Integer msgId, @RequestParam(value="auditResults") Integer auditResults,
                               @RequestParam(value="auditOpinion") String auditOpinion, @RequestParam(value="userId") Integer userId) {
        int ret = messageService.auditMsg(msgId, auditResults, auditOpinion, userId);
        return new ResponseVo(ErrorEnum.SUCCESS,ret);
    }

    @ApiOperation(value = "获取所有消息资讯，页面展现最前面四个，点击更多获取其他的", notes = "获取所有消息资讯，页面展现最前面四个，点击更多获取其他的")
    @RequestMapping(value = "/getMessageList", method = RequestMethod.GET)
    public ResponseVo getMessageList() {
        List<MessageVo> list = messageService.getMessageList();
        return new ResponseVo(ErrorEnum.SUCCESS,list);
    }
}
