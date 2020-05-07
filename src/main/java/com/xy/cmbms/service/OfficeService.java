package com.xy.cmbms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.cmbms.entity.dos.Office;
import com.xy.cmbms.entity.vos.OfficeVo;

/**
 * @author Xieyong
 * @date 2020/1/14 - 17:12
 */
public interface OfficeService extends IService<Office> {

    /**
     * 注册机构组织
     */
    int RegistOffice(OfficeVo officeVo);
}
