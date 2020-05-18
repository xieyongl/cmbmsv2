package com.xy.cmbms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.Audit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Xieyong
 * @date 2020/2/14 - 13:57
 */
@Repository
public interface AuditMapper extends BaseMapper<Audit> {

    void updateAuditMsg(@Param("msgId") Integer msgId,
                        @Param("auditOpinion") int auditOpinion,
                        @Param("userId") Integer userId);
}
