package com.asianwallets.base.service.impl;

import java.util.*;

import com.asianwallets.base.dao.InstitutionChannelMapper;
import com.asianwallets.base.dao.InstitutionProductMapper;
import com.asianwallets.base.service.InstitutionProductChannelService;
import com.asianwallets.common.dto.InstitutionProductChannelDTO;
import com.asianwallets.common.entity.InstitutionChannel;
import com.asianwallets.common.entity.InstitutionProduct;
import com.asianwallets.common.utils.ArrayUtil;
import com.asianwallets.common.utils.IDS;
import com.asianwallets.common.vo.InstitutionProductChannelVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InstitutionProductChannelServiceImpl implements InstitutionProductChannelService {

    @Autowired
    private InstitutionProductMapper institutionProductMapper;

    @Autowired
    private InstitutionChannelMapper institutionChannelMapper;

    /**
     * 新增机构关联产品通道信息
     *
     * @param username                         用户名
     * @param institutionProductChannelDTOList 机构产品通道输入实体集合
     * @return 修改条数
     */
    @Override
    @Transactional
    public int addInstitutionProductChannel(String username, List<InstitutionProductChannelDTO> institutionProductChannelDTOList) {
        for (InstitutionProductChannelDTO institutionProductChannelDTO : institutionProductChannelDTOList) {
            //机构产品信息
            InstitutionProduct institutionProduct = new InstitutionProduct();
            String insProId = IDS.uuid2();
            institutionProduct.setId(insProId);
            institutionProduct.setInstitutionId(institutionProductChannelDTO.getInstitutionId());
            institutionProduct.setProductId(institutionProductChannelDTO.getProductId());
            institutionProduct.setInstitutionName(institutionProductChannelDTO.getInstitutionName());
            institutionProduct.setProductAbbrev(institutionProductChannelDTO.getProductAbbrev());
            institutionProduct.setCreateTime(new Date());
            institutionProduct.setCreator(username);
            institutionProductMapper.insert(institutionProduct);
            if (!ArrayUtil.isEmpty(institutionProductChannelDTO.getChannelIdList())) {
                //机构通道信息
                List<InstitutionChannel> institutionChannelList = Lists.newArrayList();
                for (String channelId : institutionProductChannelDTO.getChannelIdList()) {
                    InstitutionChannel institutionChannel = new InstitutionChannel();
                    institutionChannel.setId(IDS.uuid2());
                    institutionChannel.setInsProId(insProId);
                    institutionChannel.setChannelId(channelId);
                    institutionChannel.setCreateTime(new Date());
                    institutionChannel.setCreator(username);
                    institutionChannelList.add(institutionChannel);
                }
                institutionChannelMapper.insertList(institutionChannelList);
            }
        }
        return 1;
    }

    /**
     * 修改机构关联产品通道信息
     *
     * @param username                         用户名
     * @param institutionProductChannelDTOList 机构产品通道输入实体集合
     * @return 修改条数
     */
    @Override
    @Transactional
    public int updateInsProChaByInsId(String username, List<InstitutionProductChannelDTO> institutionProductChannelDTOList) {
        //查询机构产品中间表ID
        List<String> insProIdList = institutionProductMapper.selectIdListByInstitutionId(institutionProductChannelDTOList.get(0).getInstitutionId());
        if (!ArrayUtil.isEmpty(insProIdList)) {
            //删除机构通道中间表信息
            institutionChannelMapper.deleteByInsProIdList(insProIdList);
        }
        //删除机构产品中间表信息
        institutionProductMapper.deleteByInstitutionId(institutionProductChannelDTOList.get(0).getInstitutionId());
        for (InstitutionProductChannelDTO institutionProductChannelDTO : institutionProductChannelDTOList) {
            //机构产品信息
            InstitutionProduct institutionProduct = new InstitutionProduct();
            String insProId = IDS.uuid2();
            institutionProduct.setId(insProId);
            institutionProduct.setInstitutionId(institutionProductChannelDTO.getInstitutionId());
            institutionProduct.setProductId(institutionProductChannelDTO.getProductId());
            institutionProduct.setInstitutionName(institutionProductChannelDTO.getInstitutionName());
            institutionProduct.setProductAbbrev(institutionProductChannelDTO.getProductAbbrev());
            institutionProduct.setCreateTime(new Date());
            institutionProduct.setUpdateTime(new Date());
            institutionProduct.setCreator(username);
            institutionProduct.setModifier(username);
            institutionProductMapper.insert(institutionProduct);
            if (!ArrayUtil.isEmpty(institutionProductChannelDTO.getChannelIdList())) {
                List<InstitutionChannel> institutionChannelList = Lists.newArrayList();
                for (String channelId : institutionProductChannelDTO.getChannelIdList()) {
                    InstitutionChannel institutionChannel = new InstitutionChannel();
                    institutionChannel.setId(IDS.uuid2());
                    institutionChannel.setInsProId(insProId);
                    institutionChannel.setChannelId(channelId);
                    institutionChannel.setCreateTime(new Date());
                    institutionChannel.setUpdateTime(new Date());
                    institutionChannel.setCreator(username);
                    institutionChannel.setModifier(username);
                    institutionChannelList.add(institutionChannel);
                }
                institutionChannelMapper.insertList(institutionChannelList);
            }
        }
        return 1;
    }

    /**
     * 根据机构ID查询机构关联产品通道信息
     *
     * @param username 用户名
     * @param insId    机构ID
     * @return 机构产品通道输出实体集合
     */
    @Override
    public List<InstitutionProductChannelVO> getInsProChaByInsId(String username, String insId) {
        return institutionProductMapper.selectRelevantInfoByInstitutionId(insId);
    }
}