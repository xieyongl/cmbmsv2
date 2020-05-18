package com.xy.cmbms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cmbms.constant.BaseConstant;
import com.xy.cmbms.entity.dos.Office;
import com.xy.cmbms.entity.dos.User;
import com.xy.cmbms.entity.dtos.AuditOfficeDto;
import com.xy.cmbms.entity.vos.OfficeVo;
import com.xy.cmbms.enums.ErrorEnum;
import com.xy.cmbms.exception.BusinessException;
import com.xy.cmbms.mapper.OfficeMapper;
import com.xy.cmbms.mapper.UserMapper;
import com.xy.cmbms.service.OfficeService;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Resource
    private UserMapper userMapper;

    public int RegistOffice(OfficeVo officeVo) {
        //1.判断当前机构组织名是否已经存在
        int count = officeMapper.isExistByOfficeName(officeVo.getName());
        if (count != 0) {
            throw new BusinessException(ErrorEnum.OFFICE_NAME_ISEXIST);
        }
        int userId = officeVo.getUserId();
        User user = userMapper.selectById(userId);
        //判断当前用户是否是老师，只有老师才能注册机构
        if (user.getUserType() != 2) {
            return 0;
        }

        //新增机构
        Office office = new Office();
        dozerBeanMapper.map(officeVo,office);

        office.setCreateBy(officeVo.getUserId());
        office.setPrimaryPerson(userId);
        office.setCreateDate(new Date());
        office.setUpdateDate(new Date());
        office.setDelFlag(BaseConstant.ONE);
        office.setState(1);
        officeMapper.insert(office);
        int officeId = office.getId();

        return officeId;
    }

    /**
     * 获取所有机构信息
     */
    public List<OfficeVo> getAllOffice(int flag) {
        List<OfficeVo> list = officeMapper.getAllOffice(flag);
        return list;
    }

    /**
     * 审核机构
     */
    public int auditOffice(AuditOfficeDto auditOfficeDto) {
        int opinion = auditOfficeDto.getAuditOpinion();
        int officeId = auditOfficeDto.getOfficeId();
        int userId = auditOfficeDto.getUserId();
        int auditUserId = auditOfficeDto.getAuditUserId();

        if (opinion == 3) { //审核不通过
            officeMapper.auditOffice(officeId, auditUserId, opinion, null);
        } else { //审核通过
            officeMapper.auditOffice(officeId, auditUserId, opinion, userId);
            userMapper.relateToOffice(userId, officeId);
            userMapper.updateUserType(userId, 4);
        }
        return 1;
    }
    /**
     * 模糊查询机构
     */
    public List<OfficeVo> getOfficeByQuery(String officeName) {
        List<OfficeVo> list = new ArrayList<>();
        list = officeMapper.getOfficeByQuery(officeName);
        return list;
    }
}
