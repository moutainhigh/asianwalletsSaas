package com.asianwallets.base.dao;

import com.asianwallets.common.base. BaseMapper;
import com.asianwallets.common.entity.MerchantChannel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yx
 * @since 2019-12-09
 */
@Repository
public interface MerchantChannelMapper extends  BaseMapper<MerchantChannel> {

    List<MerchantChannel> selectByMerProId(@Param("merProId") String merProId);

    int deleteByMerProId(String id);

    /**
     * @Author YangXu
     * @Date 2019/4/30
     * @Descripate  查询通道code
     * @return
     **/
    @Select("select cha_ban_id from merchant_channel where mer_pro_id = #{merProId} and enabled = true order by sort")
    List<String> selectChannelCodeByMerProId(String merProId);
}
