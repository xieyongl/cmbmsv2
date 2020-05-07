package com.xy.cmbms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cmbms.constant.BaseConstant;
import com.xy.cmbms.entity.dos.Audit;
import com.xy.cmbms.entity.dos.Message;
import com.xy.cmbms.entity.vos.MessageVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.exception.BusinessException;
import com.xy.cmbms.mapper.AuditMapper;
import com.xy.cmbms.mapper.MessageMapper;
import com.xy.cmbms.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:51
 */
@Service
public class MessageServiceImp extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private AuditMapper auditMapper;

    public int saveMsg(MessageVo messageVo) {

        if (StringUtils.isEmpty(messageVo.getTitle())) {
            throw new BusinessException(ErrorEnum.TITLE_NOT_NULL);
        }
        if (StringUtils.isEmpty(messageVo.getContent())) {
            throw new BusinessException(ErrorEnum.CONTENT_NOT_NULL);
        }

        //插入消息表
        Message message = new Message();
        message.setMsgType(messageVo.getMsgType());
        message.setTitle(messageVo.getTitle());
        message.setContent(messageVo.getContent());
        message.setPic(messageVo.getPic());
        message.setCreateBy(messageVo.getUserId());
        message.setCreateTime(new Date());
        message.setPublish(BaseConstant.ZERO);
        message.setDel_flag(BaseConstant.ONE);
        messageMapper.insert(message);

        Integer msgId = message.getId();

        //插入审核表
        Audit audit = new Audit();
        audit.setMsgId(msgId);
        audit.setCreateBy(messageVo.getUserId());
        audit.setCreateTime(new Date());
        audit.setDelFlag(BaseConstant.ONE);
        auditMapper.insert(audit);

        return msgId;

    }

    public List<MessageVo> toAuditMsg() {
        List<MessageVo> msg = messageMapper.toAuditMsg();
        return msg;
    }

    public MessageVo getMessage(Integer msgId){
        MessageVo messageVo = messageMapper.getMessage(msgId);
        return messageVo;
    }


    public List<MessageVo> getMessageList() {
        List<MessageVo> list = new ArrayList<>();
        if (StringUtils.isEmpty(list)) {
            throw new BusinessException(ErrorEnum.NO_MESSAGE);
        }
        list = messageMapper.getMessageList();
        return list;
    }



    public int auditMsg(Integer msgId, Integer auditResults, String auditOpinion, Integer userId) {
        Audit audit = messageMapper.selectAuditByMsgId(msgId);
        if (!StringUtils.isEmpty(audit.getAuditResults())) {
            throw new BusinessException(ErrorEnum.AUDIT_IS_OK);
        }
        auditMapper.updateAuditMsg(msgId, auditResults, auditOpinion, userId);
        return 1;
    }


}
