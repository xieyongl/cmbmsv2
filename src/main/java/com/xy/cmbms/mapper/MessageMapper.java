package com.xy.cmbms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.Audit;
import com.xy.cmbms.entity.dos.Message;
import com.xy.cmbms.entity.vos.MessageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xieyong
 * @date 2019/12/16 - 12:54
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    //根据消息id获取待审核信息
    List<MessageVo> toAuditMsg();

    MessageVo getMessage(@Param("msgId") Integer msgId);

    List<MessageVo> getMessageList();

    Audit selectAuditByMsgId(@Param("msgId") Integer msgId);
}
