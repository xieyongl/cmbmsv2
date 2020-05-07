package com.xy.cmbms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cmbms.constant.BaseConstant;
import com.xy.cmbms.entity.dos.Office;
import com.xy.cmbms.entity.vos.OfficeVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.exception.BusinessException;
import com.xy.cmbms.mapper.OfficeMapper;
import com.xy.cmbms.service.OfficeService;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Xieyong
 * @date 2020/1/14 - 17:11
 */
@Service
public class OfficeServiceImpl extends ServiceImpl<OfficeMapper, Office> implements OfficeService {

    @Resource
    private OfficeMapper officeMapper;

    @Resource
    private DozerBeanMapper dozerBeanMapper;

    public int RegistOffice(OfficeVo officeVo) {
        //1.判断当前机构组织名是否已经存在
        int count = officeMapper.isExistByOfficeName(officeVo.getName());
        if (count != 0) {
            throw new BusinessException(ErrorEnum.OFFICE_NAME_ISEXIST);
        }
        Office office = new Office();
        dozerBeanMapper.map(officeVo,office);

        office.setCreateBy(officeVo.getUserId());
        office.setCreateDate(new Date());
        office.setUpdateDate(new Date());
        office.setDelFlag(BaseConstant.ONE);
        officeMapper.insert(office);
        int officeId = office.getId();

        return officeId;
    }
}
