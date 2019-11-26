package com.asianwallets.base.service;

import com.asianwallets.common.dto.InstitutionDTO;
import com.asianwallets.common.entity.Institution;
import com.asianwallets.common.base.BaseService;
import com.asianwallets.common.entity.InstitutionAudit;
import com.asianwallets.common.vo.SysUserVO;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 机构表 服务类
 * </p>
 *
 * @author yx
 * @since 2019-11-22
 */
public interface InstitutionService extends BaseService<Institution> {


    /**
     * @return
     * @Author YangXu
     * @Date 2019/11/25
     * @Descripate 添加机构
     **/
    int addInstitution(String name, InstitutionDTO institutionDTO);

    /**
     * @return
     * @Author YangXu
     * @Date 2019/11/25
     * @Descripate 修改机构
     **/
    int updateInstitution(String name, InstitutionDTO institutionDTO);

    /**
     * @return
     * @Author YangXu
     * @Date 2019/11/25
     * @Descripate 查询机构信息
     **/
    PageInfo<Institution> pageFindInstitution(InstitutionDTO institutionDTO);


    /**
     * @return
     * @Author YangXu
     * @Date 2019/11/25
     * @Descripate 分页查询机构审核信息列表
     **/
    PageInfo<InstitutionAudit> pageFindInstitutionAudit(InstitutionDTO institutionDTO);


    /**
     * @return
     * @Author YangXu
     * @Date 2019/11/25
     * @Descripate 根据机构Id查询机构信息详情
     **/
    Institution getInstitutionInfo(String id);


    /**
     * @return
     * @Author YangXu
     * @Date 2019/11/25
     * @Descripate 根据机构Id查询机构信息详情
     **/
    InstitutionAudit getInstitutionInfoAudit(String id);

    /**
     * @return
     * @Author YangXu
     * @Date 2019/11/25
     * @Descripate 审核机构信息
     **/
    int auditInstitution(String username, String institutionId, Boolean enabled, String remark);
}