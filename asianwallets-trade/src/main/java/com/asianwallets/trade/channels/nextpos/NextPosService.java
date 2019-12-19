package com.asianwallets.trade.channels.nextpos;

import com.asianwallets.common.entity.OrderRefund;
import com.asianwallets.common.response.BaseResponse;

public interface NextPosService {



    /**
     * @Author YangXu
     * @Date 2019/12/19
     * @Descripate 退款接口
     * @return
     **/
    BaseResponse refund(OrderRefund orderRefund);
}
