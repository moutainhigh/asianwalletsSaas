package com.asianwallets.common.dto.upi.iso;

import com.alibaba.fastjson.JSON;
import com.asianwallets.common.dto.th.ISO8583.ISO8583DTO;
import com.asianwallets.common.dto.th.ISO8583.ISO8583Util;
import com.asianwallets.common.dto.th.ISO8583.NumberStringUtil;
import com.asianwallets.common.utils.IDS;

import java.util.Arrays;
import java.util.Map;

/**
 * @description:
 * @author: YangXu
 * @create: 2020-06-10 14:49
 **/
public class Demo {

    //AW3 商户终端参数: POS商户联调(自带机)
    //商户号: 000000000003421 (测试环境)
    //终端号: 00001903 (测试环境)
    //主密钥索引: 002
    //        210.48.142.168 6000 走网控（主机）
    //        210.48.142.168 7000 走网控（备机）
    //TPDU NII:
    //        006(测试环境)  6000060000
    //        007(生产环境)  6000070000
    private static String ip = "210.48.142.168";
    private static String port = "7000";
    private static String merchantId = "000000000003421";
    private static String terminalId = "00001903";
    private static String key = "861B7FBD78A6E196";

    public static void main(String[] args) throws Exception {
        String domain11 = IDS.uniqueID().toString().substring(0, 6);

        ISO8583DTO iso8583DTO = new ISO8583DTO();
        iso8583DTO.setMessageType("0800");
        iso8583DTO.setSystemTraceAuditNumber_11(domain11);
        //受卡机终端标识码 (设备号)
        iso8583DTO.setCardAcceptorTerminalIdentification_41(terminalId);
        //受卡方标识码 (商户号)
        iso8583DTO.setCardAcceptorIdentificationCode_42(merchantId);
        //自定义域
        iso8583DTO.setReservedPrivate_60("00000002003");//01000001000000000
        iso8583DTO.setReservedPrivate_63("000");
        //扫码组包
        //String isoMsg = UpiIsoUtil.packISO8583DTO(iso8583DTO, null);
        //String sendMsg = "6000060000" +"601410190121"+ isoMsg;
        //String strHex2 = String.format("%04x", sendMsg.length() / 2).toUpperCase();
        //sendMsg = strHex2 + sendMsg;
        //System.out.println(" ===  扫码sendMsg  ====   " + sendMsg);
        //
        ////Map<String, String> respMap = UpiIsoUtil.sendTCPRequest(ip, port, sendMsg.getBytes());
        //Map<String, String> respMap = UpiIsoUtil.sendTCPRequest(ip, port, NumberStringUtil.str2Bcd(sendMsg));
        //String result = respMap.get("respData");
        String result ="009760000000066014101901210810002000010AC1001410381708183258103131363332373633313737373030303030303139303330303030303030303030303334323100240000190300000000000342100011000000020030006100238FE1736B6B3C7797CAF22751380E1AC8FEC1766B669A9DAAE21EDD62BD98C67626F365125B3F2B1CD7548687C8313A22BD699118F111361944BA01";
        System.out.println(" ====  扫码result  ===   " + result);
        //解包
        ISO8583DTO iso8583DTO1281 = UpiIsoUtil.unpackISO8583DTO(result);
        System.out.println("扫码结果:" + JSON.toJSONString(iso8583DTO1281));
    }
}
