package com.asianwallets.common.response;

/**
 * @version v1.0.0
 * @classDesc: 功能描述: 业务错误码定位
 * @createTime 2018年7月2日 下午4:25:33
 * @copyright: 上海众哈网络技术有限公司
 */
public enum EResultEnum {

    SUCCESS("200"),//成功
    ERROR("50000"),//服务器繁忙，请稍后重试
    USER_IS_NOT_LOGIN("50001"),//用户未登录
    PARAMETER_IS_NOT_PRESENT("50002"),//输入参数不能为空
    PARAMETER_IS_NOT_INVALID("50003"),//输入参数不合法

    REQUEST_REMOTE_ERROR("52002"),//服务内部错误
    DECRYPTION_ERROR("52003"),//签名不匹配
    USER_OR_PASSWORD_INCORRECT("52004"),//用户名或密码不正确
    USER_NOT_ENABLE("52005"),//用户名已禁用
    TRADE_PASSWORD_ERROR("52006"),//交易密码不正确
    NAME_EXIST("52007"),//机构名称已存在
    SIGNATURE_CANNOT_BE_EMPTY("52008"),//签名不能为空
    SIGNATURE_ERROR("52009"),//签名错误
    PERMISSION_NOT_FULL("52010"),//用户权限不足
    ORIGINAL_PASSWORD_ERROR("52011"),//原密码错误


    USER_EXIST("10001"),//账号已存在
    ROLE_EXIST("10002"),//角色已存在
    ROLE_NO_EXIST("10003"),//角色不存在
    USER_NOT_EXIST("10004"),//用户不存在
    INSTITUTION_NOT_EXIST("10005"),//机构信息不存在
    MENU_NOT_EXIST("10006"),//权限不存在
    TOKEN_IS_INVALID("10007"),//Token不合法
    ORDER_NOT_EXIST("10008"),//订单不存在
    ORDER_STATUS_IS_WRONG("10009"),//订单交易状态不合法
    CHANNEL_IS_NOT_EXISTS("10010"),//通道信息不存在
    ORDER_CREATION_FAILED("10011"), //订单创建失败
    INSTITUTION_CODE_NO_EXIST("10012"), //机构编号不存在
    INSTITUTION_STATUS_ABNORMAL("10013"),//机构状态异常
    MERCHANT_ORDER_ID_EXIST("10014"),//商户订单号已存在
    GET_PRODUCT_INFO_ERROR("10015"),//获取产品信息异常
    PRODUCT_CURRENCY_NO_SUPPORT("10016"),//当前币种不支持
    REFUND_AMOUNT_NOT_LEGAL("10017"),//金额不合法
    GET_CHANNEL_INFO_ERROR("10018"),//获取通道信息异常
    REPEAT_ORDER_REQUEST("10019"),//重复请求
    ORDER_NOT_SUPPORT_REVERSE("10020"),//冲正请求失败
    PRE_ORDERS_STATUS_ERROR("10021"),//原订单不是预授权完成的订单,不能预授权完成撤销
    ONLINE_ORDER_IS_NOT_ALLOW_UNDO("10022"),//撤销请求失败
    AUDIT_INFO_EXIENT("10023"),//记录已存在，等待审核中
    INSTITUTION_AND_MERCHANT_NOT_RELATED("10024"),//机构和商户未关联
    MERCHANT_CARD_CODE_IS_ENABLE("10025"),//静态码已禁用
    EFFECTTIME_IS_NULL("10026"),//生效时间不能为空
    MERCHANT_ACCOUNT_CURRENCY_IS_NOT_EXIST("10027"),//当前商户不存在该币种的账户
    BANLANCE_NOT_FOOL("10028"),//账户余额不足
    DEVICE_CODE_INVALID("10029"),//设备编号不正确
    DEVICE_OPERATOR_INVALID("10030"),//设备操作员不正确
    FILE_CONTENT_ERROR("10031"),//文件内容错误
    TRADE_COUNT_ERROR("10032"),//超过单日最大笔数限制
    TRADE_AMOUNT_ERROR("10033"),//超过单日限额
    QUERY_ORDER_ERROR("10034"),//订单查询异常
    CALLBACK_PARAMETER_IS_NULL("10035"),//回调参数为空
    BANK_INSTITUTION_NUMBER_NOT_EXIST("10036"),//银行机构号不存在
    LIMIT_AMOUNT_ERROR("10037"),//超过单笔金额限制
    RATE_TYPE_IS_DIFFERENT("10038"),//机构的产品费率类型和代理商的产品费率类型不一致
    NAME_ERROR("10039"),//文件名称不合法
    PAYMENT_ABNORMAL("10040"),//支付异常
    LESS_THAN_EEOR("10041"),//小于单笔金额限制
    ORDER_NOT_SETTLE("10042"),//暂不支持部分退款，请稍后尝试
    TIAOZHANG_RECORD_IS_NOT_EXIST("10043"),//调账记录不存在
    AGENCY_INSTITUTION_NOT_EXIST("10044"),//代理机构信息不存在
    SUCCESS_TRADE_IS_NOT_EXIST("10045"),//暂无成功交易记录
    FILE_EXIST("10046"),//文件已上传
    ORDER_INFO_NO_MATCHING("10047"),//订单信息不匹配
    PRODUCT_CODE_IS_NOT_NULL("10048"), //产品编号不能为空
    INSTITUTION_PRODUCT_CHANNEL_NOT_EXISTS("10049"),//支付通道未配置
    REPORT_QJS_ERROR("10050"),//上报清结算失败
    ORDER_PAID_ERROR("10051"),//订单已支付
    AGENCY_INSTITUTION_IS_DISABLE("10052"),//代理机构已禁用
    CHANNEL_CURRENCY_DOES_NOT_EXIST("10053"),//通道币种不存在
    DATA_IS_NOT_EXIST("10054"),//数据不存在
    REFUND_CANCEL_ERROR("10055"),//该交易已撤销，不支持退款
    CALLBACK_ADDRESS_IS_NULL("10056"),//回调地址为空
    EFFECTTIME_IS_ILLEGAL("10057"),//生效时间不合法
    REFUND_CANCEL_OR_REVERSE("10058"),//该交易已撤销或者已冲正，不支持退款
    CALCCHANNEL_POUNDAGE_FAILURE("10059"),//计算通道手续费失败
    CALCCHANNEL_GATEWAYPOUNDAGE_FAILURE("10060"),//计算通道网关手续费失败
    PRODUCT_ID_IS_NOT_NULL("10061"), //产品ID不能为空
    INSTITUTION_IS_DISABLE("10062"),//机构已禁用
    MERCHANT_ACCOUNT_CURRENCY_IS_DISABLE("10063"),//当前商户该币种的账户已禁用
    CANCEL_ORDER_SUCCESS_IS_NOT_UNDO("10064"),//撤销成功的订单不能再撤销
    INSTITUTION_PRODUCT_IS_NOT_EXIST("10065"),//机构产品不存在
    INSTITUTION_PRODUCT_STATUS_ABNORMAL("10066"),//机构产品状态异常
    CHANNEL_STATUS_ABNORMAL("10067"),//支付通道不可用
    INSUFFICIENT_ACCOUNT_BALANCE("10068"),//账户余额不足
    ROLE_PERMISSION_IS_NOT_NULL("10069"),//角色权限不能为空
    REFUNDING("10070"),//退款中
    REFUND_FAIL("10071"),//退款失败
    SIGN_TYPE_IS_NULL("10072"),//签名类型不能为空
    TRADE_FEE_IS_NULL("10073"),//交易手续费为空
    FEE_CURRENCY_IS_NULL("10074"),//手续费币种为空
    RATE_IS_NULL("10075"),//手续费汇率为空
    RATE_IS_ILLEGAL("10076"),//机构的费率不能小于代理商的费率
    ACCOUNT_IS_NOT_EXIST("10077"),//账户不存在
    TRADE_STATUS_IS_ERROR("10078"),//结算状态异常
    INFORMATION_IS_ILLEGAL("10079"),//信息不合法
    UPDATE_FAILED("10080"),//更新失败
    DEFALUT_BANK_ACCOUT_CODE_IS_EXISTS("10081"),//该机构相同的银行卡币种和结算币种的默认银行卡已存在
    ENABLED_BANK_ACCOUT_CODE_IS_ERROR("10082"),//已禁用的银行卡不能设置为默认银行卡
    CONNECT_LIMIT_ERROR("10083"),//该机构不支持直连
    FAILED_TO_CALCULATE_REFUND_FEE("10084"),//计算退款手续费失败
    SETTLEMENT_AMOUNT_TOO_SMALL("10085"),//结算金额小于0
    FREEZE_STATE_IS_ILLEGAL("10086"),//冻结状态不合法
    THAWED_STATE_IS_ILLEGAL("10087"),//解冻状态不合法
    FUNDS_STATUS_IS_ILLEGAL("10088"),//资金状态不合法
    ACCOUNT_IS_EXCEPTION("10089"),//账户异常
    INSUFFICIENT_FROZEN_ACCOUNT_BALANCE("10090"),//冻结账户余额不足
    DOMAIN_NOT_MATCH("10091"),//请求网站url不合法
    SERVER_CALLBACK_FORMAT_ERROR("10092"),//服务器回调地址格式错误
    CHANNEL_SERVICE_NAME_NO_MATCH("10093"),//通道服务名称不匹配
    NOT_WITHIN_THE_CHANNEL_LIMIT("10094"),//通道限额不合法
    BANK_MAPPING_NO_EXIST("10095"),//银行映射不存在
    CHANNEL_OR_BANK_DOES_NOT_EXIST("10096"),//通道代码或者银行名不存在
    PRODUCT_NAME_DOES_NOT_EXIST("10097"),//商品名称不能为空
    SERVER_URL_CANNOT_BE_EMPTY("10098"),//服务器回调地址不能为空
    CASH_COUNTER_PARAMETER_DECRYPTION_ERROR("10099"),//收银台参数解密错误
    INSTITUTION_CONFIGURATION_URL_FORMAT_ERROR("10100"),//机构后台配置URL格式错误
    BILLING_CORRESPONDING_BANK_CARD_DOES_NOT_EXIST("10101"),//结算对应银行卡不存在
    INSUFFICIENT_WITHDRAWAL_AMOUNT("10102"),//提款金额不足
    INSUFFICIENT_MINIMUM_AMOUNT("10103"),//不足最小起结金额
    ORDER_PAYMENT_FAILED("10104"),//订单支付失败
    MERCHANT_IS_DISABLED("10105"),//商户被禁用
    MERCHANT_PRODUCT_IS_DISABLED("10106"),//商户产品被禁用
    MERCHANT_PRODUCT_CONFIGURATION_INFORMATION_ERROR("10107"),//商户产品配置信息错误
    DCC_IS_NOT_OPEN("10108"),//DCC未开通
    PRODUCT_DOES_NOT_EXIST("10110"),//产品信息不存在
    MERCHANT_PRODUCT_DOES_NOT_EXIST("10111"),//商户产品信息不存在
    CHANNEL_BANK_DOES_NOT_EXIST("10112"),//通道银行信息不存在
    NOT_A_GROUP_MERCHANT("10113"),//不是集团商户
    REVERSAL_ERROR("10114"),//冲正失败
    CODE_CARD_INFORMATION_DOES_NOT_EXIST("10115"),//码牌信息不存在

    /****通知模块的错误信息从20001开始--杨善龙*****/
    NOTICE_ID_IS_NOT_NULL("20001"),//公告id不能为空
    NOTICE_CATEGORY_IS_NOT_NULL("20002"),//公告类别不能为空
    NOTICE_LANGUAGE_IS_NOT_NULL("20003"),//公告语言不能为空
    NOTICE_TITLE_IS_NOT_NULL("20004"),//公告标题不能为空
    NOTICE_CONTEXT_IS_NOT_NULL("20005"),//公告内容不能为空
    UPLOAD_FILE_ERROR("20006"),//上传失败，请重试
    NOT_SUPPORT_REFUND("20007"),//不支持退款
    CASHIER_EXCHANGE_TIME_DOES_NOT_EXIST("20008"),//收银台换汇时间不存在
    ORIGINAL_ORDER_DOES_NOT_EXIST("20009"),//收银台原始订单不存在
    INSTITUTIONAL_PRODUCTS_DO_NOT_EXIST("20010"),//产品未开通
    SYS_ERROR_CREATE_ORDER_FAIL("20011"),//系统错误,订单创建失败
    ORDER_CURRENCY_IS_NOT_AVAILABLE("20012"),//订单币种不可用

    /****算费模块以及其他基础信息模块的错误信息从30001开始--沈欣然*****/
    LANGUAGE_EXIST("30001"),//语言已存在
    DICINFO_TYPE_VALUE_NOT_EXIST("30002"),//字典类型不存在
    DICINFO_LANGUAGE_IS_NULL("30003"),//语言不能为空
    REPEATED_ADDITION("30004"),//信息已存在
    INSTITUTION_REQUST_PARA_IS_NOT_EXIST("30005"),//机构请求参数信息不存在
    DICINFO_TYPE_IS_NULL("30006"),//字典信息不能为空
    DICINFO_NAME_IS_NULL("30007"),//字典名称不能为空
    DICINFO_CODE_IS_NULL("30008"),//字典数据识别码不能为空
    DICINFO_ID_IS_NULL("30009"),//字典ID不能为空
    ENABLE_IS_NULL("30010"),//启用或禁用不能为空
    DICINFO_TYPEID_IS_NULL("30011"),//字典类型不能为空
    DICINFO_ILLEGAL_PARAMETER_EXIST("30012"),//字典添加非法参数
    ATTESTATION_PUBKEY_IS_NULL("30013"),//公钥不能为空
    ATTESTATION_ID_IS_NULL("30014"),//密钥ID不能为空
    INSTITUTION_INFORMATION_EXPORT_FAILED("30015"),//信息导出失败
    PAYMENTMODE_ADD_PAYCODE_IS_NULL("30016"),//支付方式不能为空
    PAYMENTMODE_ADD_DEALCODE_IS_NULL("30017"),//交易类型不能为空
    PAYMENTMODE_EXIST("30018"),//支付方式已存在
    DEVICE_VENDOR_NOT_EXIST("30019"),//设备厂商不存在
    DEVICE_MODEL_NOT_EXIST("30020"),//设备型号不存在
    DEVICE_IMEL_EXIST("30021"),//设备IMEL号已存在
    DEVICE_SN_EXIST("30022"),//设备SN号已存在
    DEVICE_HAS_BEEN_BOUND("30023"),//设备已被绑定
    DEVICE_BINDING_FAILED("30024"),//设备绑定失败
    DEVICE_NOT_AVAILABLE("30025"),//设备不可用
    DEVICE_UNBIND_FAILED("30026"),//设备解绑失败
    DEVICE_OPERATION_FAILED("30027"),//操作失败
    KEY_GENERATION_FAILED("30028"),//密钥生成失败
    ACCOUNT_TYPE_IS_NULL("30029"),//入账账户类型不能为空
    PRODUCT_CHANNEL_CURRENCY_NO_SAME("30030"),//通道与产品币种不一致
    INFORMATION_DOES_NOT_EXIST("30031"),//信息不存在
    MCC_EXIST("30032"),//mcc已存在
    MCC_DOES_NOT_EXIST("30033"),//mcc不存在
    DICINFO_CURRENCY_DEFAULT_IS_NULL("30034"),//币种默认值不能为空
    MERCHANT_DOES_NOT_EXIST("30036"),//商户不存在
    SHOP_CODE_EXIST("30037"),//店铺编号已存在
    EXPIR_TIME_IS_ERROR("30038"),//过期时间必须大于当前系统时间
    SECRET_IS_NOT_EXIST("30039"),//密钥不存在


    /****汇率模块的错误信息从40001开始--许文奇*****/
    ERROR_REDIS_UPDATE("40001"), //同步数据到redis发生错误
    EXRATE_ID_IS_NOT_NULL("40002"),//汇率id不能为空
    LOCAL_CURRENCY_IS_NOT_NULL("40003"),//本位币种不能为空  代码里有用不要删除
    FOREIGN_CURRENCY_IS_NOT_NULL("40004"),//目标币种不能为空 代码里有用不要删除
    RATE_IS_NOT_NULL("40005"),//汇率值不能为空 代码里有用不要删除
    EXCHANGERATE_IS_NOT_EXIST("40006"), //汇率信息不存在
    USER_EMAIL_IS_NOT_NULL("40007"), //用户邮箱不能为空
    HOLIDAYS_ID_IS_NOT_NULL("40008"), //节假日id不能为空
    HOLIDAYS_COUNTRY_IS_NOT_NULL("40009"), //国家不能为空
    FILE_FORMAT_ERROR("40010"),//文件格式错误
    HOLIDAYS_DATE_IS_NOT_NULL("40011"), //节假日日期不能为空
    HOLIDAYS_INFO_EXIST("40012"),//节假日信息已存在
    HOLIDAYS_ADD_TIME_EXPIRED("40013"),//添加的节日时间已过期
    EXCEL_FORMAT_INCORRECT("40014"),//excel文件内格式不正确
    EXCEEDING_UPLOAD_LIMIT("40015"),//超过最大导入条数300
    IMPORT_REPEAT_ERROR("40016"),//导入信息重复
    HOLIDAYS_NAME_IS_NOT_NULL("40017"),//节假日名称为空
    LOCAL_FOREIGN_CURRENCY_IS_SAME("40018"),//本币和外币不能相同

    TICKET_NOT_EXTENT("60001"),//票券不存在或已使用
    TICKET_INFO_EXCEPTION("60002"),//票券信息异常
    TICKET_TIME_EXCEPTION("60003"),//票券期限异常
    EQUITY_DOES_NOT_EXIST("60005"),//机构权益不存在
    OTA_NOT_SUPPORT_CANCLE("60006"),//OTA平台不支持撤销
    OTA_ACTIVITY_AMOUNT_IS_ILLEGAL("60007"),//活动数量不合法
    EVENT_START_TIME_IS_ILLEGAL("60008"),//活动开始时间非法
    EVENT_END_TIME_IS_ILLEGAL("60009"),//活动结束时间非法
    UNAVAILABLE_START_TIME_ILLEGAL("60010"),//不可用开始时间非法
    UNAVAILABLE_END_TIME_ILLEGAL("60011"),//不可用结束时间非法
    DUPLICATE_EVENT_THEME("60012"),//活动主题重复
    ILLEGAL_NUMBER_OF_ACTIVITIES("60013"),//活动数量不合法
    TICKET_BUY_PRICE_NOT_THAN_TICKET_PRICE("60014"),//票券购买价不能大于票券面额
    UNAVAILABLE_TIME_IS_ILLEGAL("60015");//不可用时间非法


    private String code;

    private EResultEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
