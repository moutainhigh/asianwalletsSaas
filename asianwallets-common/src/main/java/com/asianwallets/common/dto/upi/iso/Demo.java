package com.asianwallets.common.dto.upi.iso;

import com.alibaba.fastjson.JSON;
import com.asianwallets.common.dto.th.ISO8583.EcbDesUtil;
import com.asianwallets.common.dto.th.ISO8583.ISO8583DTO;
import com.asianwallets.common.dto.th.ISO8583.ISO8583Util;
import com.asianwallets.common.dto.th.ISO8583.NumberStringUtil;
import com.asianwallets.common.utils.AESUtil;
import com.asianwallets.common.utils.IDS;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

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
    private static String key_62 = "2F9781AE38D8CAFB6D29CD8F9A88CF11C8EEC334F898A9D1507D76837864D01C0CA0D4204BE2468B83E2212BCB7909E1E2FD84C3793F09B6E2E88FD5";
    private static String key = "94A1FB75E03EADEA";

    public static void main(String[] args) throws Exception {
        test1();
        //test2();
        //test3();
    }

    private static void test1() throws Exception  {
        String domain11 = String.valueOf(System.currentTimeMillis()).substring(0, 6);

        ISO8583DTO iso8583DTO = new ISO8583DTO();
        iso8583DTO.setMessageType("0200");
        iso8583DTO.setProcessingCode_3("190000");
        iso8583DTO.setAmountOfTransactions_4("000000000009");
        iso8583DTO.setSystemTraceAuditNumber_11(domain11);
        iso8583DTO.setDateOfExpired_14("5012");
        iso8583DTO.setPointOfServiceEntryMode_22("032");
        iso8583DTO.setCardSequenceNumber_23("001");
        iso8583DTO.setPointOfServiceConditionMode_25("82");
        //受卡机终端标识码 (设备号)
        iso8583DTO.setCardAcceptorTerminalIdentification_41(terminalId);
        //受卡方标识码 (商户号)
        iso8583DTO.setCardAcceptorIdentificationCode_42(merchantId);
        iso8583DTO.setCurrencyCodeOfTransaction_49("344");

        //自定义域
        iso8583DTO.setReservedPrivate_60("22000001000600");//01000001000000000

        //银行卡号
        String var2 = "4761340000000019";
        //银行卡 磁道2信息
        String var35 = "4761340000000019=171210114991787";
        //加密信息
        //iso8583DTO.setProcessingCode_2(trkEncryption(var2, key_62));
        //iso8583DTO.setTrack2Data_35(trkEncryption(var35, key_62));
        iso8583DTO.setTrack2Data_35("B9F24EFEF4B179CF643B349A873F2FB7");
        iso8583DTO.setProcessingCode_2("6251640751492683");



        //扫码组包
        String isoMsg = UpiIsoUtil.packISO8583DTO(iso8583DTO, key);
        String sendMsg = "6000060000" +"601410190121"+ isoMsg;
        String strHex2 = String.format("%04x", sendMsg.length() / 2).toUpperCase();
        sendMsg = strHex2 + sendMsg;
        System.out.println(" ===  扫码sendMsg  ====   " + sendMsg);

        //Map<String, String> respMap = UpiIsoUtil.sendTCPRequest(ip, port, sendMsg.getBytes());
        Map<String, String> respMap = UpiIsoUtil.sendTCPRequest(ip, port, NumberStringUtil.str2Bcd(sendMsg));
        String result = respMap.get("respData");
        System.out.println(" ====  扫码result  ===   " + result);
        //解包
        ISO8583DTO iso8583DTO1281 = UpiIsoUtil.unpackISO8583DTO(result);
        System.out.println("扫码结果:" + JSON.toJSONString(iso8583DTO1281));
    }

    private static void test2() {
        String substring = key_62.substring(40, 56);
        String mac = Objects.requireNonNull(EcbDesUtil.decode3DEA("3104BAC458BA1513043E4010FD642619", substring)).toUpperCase();
        System.out.println(mac);
    }
    private static void test3() throws Exception{
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
        String isoMsg = UpiIsoUtil.packISO8583DTO(iso8583DTO, null);
        String sendMsg = "6000060000" +"601410190121"+ isoMsg;
        String strHex2 = String.format("%04x", sendMsg.length() / 2).toUpperCase();
        sendMsg = strHex2 + sendMsg;
        System.out.println(" ===  扫码sendMsg  ====   " + sendMsg);

        //Map<String, String> respMap = UpiIsoUtil.sendTCPRequest(ip, port, sendMsg.getBytes());
        Map<String, String> respMap = UpiIsoUtil.sendTCPRequest(ip, port, NumberStringUtil.str2Bcd(sendMsg));
        String result = respMap.get("respData");
        System.out.println(" ====  扫码result  ===   " + result);
        //解包
        ISO8583DTO iso8583DTO1281 = UpiIsoUtil.unpackISO8583DTO(result);
        System.out.println("扫码结果:" + JSON.toJSONString(iso8583DTO1281));
    }
    private static String trkEncryption(String str, String key) {
        //80-112 Trk密钥位
        String substring = key.substring(80, 112);
        String trk = Objects.requireNonNull(EcbDesUtil.decode3DEA("3104BAC458BA1513043E4010FD642619", substring)).toUpperCase();
        String newStr;
        if (str.length() % 2 != 0) {
            newStr = str.length() + str + "0";
        } else {
            newStr = str.length() + str;
        }
        byte[] bcd = NumberStringUtil.str2Bcd(newStr);
        return Objects.requireNonNull(EcbDesUtil.encode3DEA(trk, cn.hutool.core.util.HexUtil.encodeHexStr(bcd))).toUpperCase();
    }
}
