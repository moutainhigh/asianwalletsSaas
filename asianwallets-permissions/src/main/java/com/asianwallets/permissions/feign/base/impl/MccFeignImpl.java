package com.asianwallets.permissions.feign.base.impl;

import com.asianwallets.common.dto.MccDTO;
import com.asianwallets.common.entity.Mcc;
import com.asianwallets.common.exception.BusinessException;
import com.asianwallets.common.response.BaseResponse;
import com.asianwallets.common.response.EResultEnum;
import com.asianwallets.common.vo.MccVO;
import com.asianwallets.permissions.feign.base.MccFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * mcc
 */
@Component
public class MccFeignImpl implements MccFeign {
    @Override
    public BaseResponse inquireAllMcc() {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public BaseResponse addMcc(MccDTO mccDto) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public BaseResponse updateMcc(MccDTO mccDto) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public BaseResponse pageMcc(MccDTO mccDto) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public BaseResponse banMcc(MccDTO mccDto) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public BaseResponse importMcc(List<Mcc> list) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public List<MccVO> exportMcc(MccDTO mccDto) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }
}
