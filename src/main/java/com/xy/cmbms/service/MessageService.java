package com.xy.cmbms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.cmbms.entity.dos.Message;
import com.xy.cmbms.entity.vos.MessageVo;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:51
 */
public interface MessageService extends IService<Message> {

    int saveMsg(MessageVo messageVo);

    List<MessageVo> toAuditMsg();

    MessageVo getMessage(Integer msgId);

    List<MessageVo> getMessageList(int flag);

    int auditMsg(Integer msgId, int auditOpinion, Integer userId);

}
