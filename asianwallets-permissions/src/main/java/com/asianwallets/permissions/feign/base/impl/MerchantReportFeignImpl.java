package com.asianwallets.permissions.feign.base.impl;
import com.asianwallets.common.dto.MerchantReportDTO;
import com.asianwallets.common.entity.MerchantReport;
import com.asianwallets.common.exception.BusinessException;
import com.asianwallets.common.response.BaseResponse;
import com.asianwallets.common.response.EResultEnum;
import com.asianwallets.common.vo.MerchantReportVO;
import com.asianwallets.permissions.feign.base.MerchantReportFeign;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MerchantReportFeignImpl implements MerchantReportFeign {
    @Override
    public BaseResponse addReport(MerchantReportDTO merchantReportDTO) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public BaseResponse pageReport(MerchantReportDTO merchantReportDTO) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public BaseResponse updateReport(MerchantReportDTO merchantReportDTO) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public BaseResponse banReport(MerchantReportDTO merchantReportDTO) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public List<MerchantReportVO> exportReport(MerchantReportDTO merchantReportDTO) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }

    @Override
    public BaseResponse importMerchantReport(List<MerchantReport> merchantReportList) {
        throw new BusinessException(EResultEnum.ERROR.getCode());
    }


}
