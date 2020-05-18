package com.xy.cmbms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.cmbms.entity.dos.Office;
import com.xy.cmbms.entity.dtos.AuditOfficeDto;
import com.xy.cmbms.entity.vos.OfficeVo;

import java.util.List;

/**
 * @author Xieyong
 * @date 2020/1/14 - 17:12
 */
public interface OfficeService extends IService<Office> {

    /**
     * 注册机构组织
     */
    int RegistOffice(OfficeVo officeVo);

    /**
     * 获取所有机构信息
     */
    List<OfficeVo> getAllOffice(int flag);

    /**
     * 审核机构
     */
   int auditOffice(AuditOfficeDto auditOfficeDto);

    /**
     * 模糊查询机构
     */
    List<OfficeVo> getOfficeByQuery(String officeName);
}
