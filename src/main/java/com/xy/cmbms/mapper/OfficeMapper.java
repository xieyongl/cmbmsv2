package com.xy.cmbms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cmbms.entity.dos.Office;
import com.xy.cmbms.entity.vos.OfficeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xieyong
 * @date 2020/1/14 - 17:13
 */
@Repository
public interface OfficeMapper  extends BaseMapper<Office> {

    //根据组织名判断该组织是否存在
    int isExistByOfficeName(@Param("officeName") String officeName);


    List<OfficeVo> getAllOffice(@Param("flag") int flag);

    /**
     * 模糊查询机构
     */
    List<OfficeVo> getOfficeByQuery(@Param("officeName") String officeName);

    /**
     * 审核机构
     */
    void auditOffice(@Param("officeId") int officeId,
                     @Param("auditUserId") int auditUserId,
                     @Param("opinion") int opinion,
                     @Param("userId") Integer userId);
}
