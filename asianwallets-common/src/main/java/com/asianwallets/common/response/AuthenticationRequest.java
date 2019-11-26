package com.asianwallets.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "认证输入实体", description = "认证输入实体")
public class AuthenticationRequest {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("系统ID")
    private String sysId;

    @ApiModelProperty("设备编号")
    private String imei;

    @ApiModelProperty("权限类型")
    private Integer permissionType;

}
