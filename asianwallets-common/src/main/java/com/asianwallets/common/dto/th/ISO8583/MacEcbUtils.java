package com.asianwallets.common.dto.th.ISO8583;

/**
 * @description:
 * @author: YangXu
 * @create: 2020-05-08 16:52
 * 银联标准Mac 算法
 */
public class MacEcbUtils {

    public static void main(String[] args) {
        byte[] key = new byte[]{0x5C, (byte) 0xBE, 0x7E, 0x38, (byte) 0xA1, 0x46, (byte) 0xFD, 0x5C};
        byte[] input = new byte[]{0x01, 0x02, 0x03};
        System.out.println(NumberStringUtil.bcd2Str(getMac(key, input)));

        String req = "0086600002000080010000000038353239393939353831323030353030303030323735333330303030303030303030303030333030303030303031383532393939393538313230303530303030303030353408000020000100C000121981240852000001303030303237353338353239393939353831323030353000115000000100300003303031";
        String key2 = "38D57B7C1979CF7910677DE5BB6A56DF";
        System.out.println(new String(getMac(key2.getBytes(), req.getBytes())));
    }

    /**
     * mac计算
     *
     * @param key   mac秘钥
     * @param Input 待加密数据
     * @return
     */
    public static byte[] getMac(byte[] key, byte[] Input) {
        int length = Input.length;
        int x = length % 8;
        // 需要补位的长度
        int addLen = 0;
        if (x != 0) {
            addLen = 8 - length % 8;
        }
        int pos = 0;
        // 原始数据补位后的数据
        byte[] data = new byte[length + addLen];
        System.arraycopy(Input, 0, data, 0, length);
        byte[] oper1 = new byte[8];
        System.arraycopy(data, pos, oper1, 0, 8);
        pos += 8;
        // 8字节异或
        for (int i = 1; i < data.length / 8; i++) {
            byte[] oper2 = new byte[8];
            System.arraycopy(data, pos, oper2, 0, 8);
            byte[] t = bytesXOR(oper1, oper2);
            oper1 = t;
            pos += 8;
        }
        // 将异或运算后的最后8个字节（RESULT BLOCK）转换成16个HEXDECIMAL：
        byte[] resultBlock = bytesToHexString(oper1).getBytes();
        // 取前8个字节MAK加密
        byte[] front8 = new byte[8];
        System.arraycopy(resultBlock, 0, front8, 0, 8);
        byte[] behind8 = new byte[8];
        System.arraycopy(resultBlock, 8, behind8, 0, 8);
        byte[] desfront8 = DesUtils.encrypt(front8, key);
        // 将加密后的结果与后8 个字节异或：
        byte[] resultXOR = bytesXOR(desfront8, behind8);
        // 用异或的结果TEMP BLOCK 再进行一次单倍长密钥算法运算
        byte[] buff = DesUtils.encrypt(resultXOR, key);
        // 将运算后的结果（ENC BLOCK2）转换成16 个HEXDECIMAL asc
        byte[] retBuf = new byte[8];
        System.out.println("MAC明文:" + bytesToHexString(buff));
        // 取8个长度字节就是mac值
        System.arraycopy(bytesToHexString(buff).getBytes(), 0, retBuf, 0, 8);
        return retBuf;
    }

    /**
     * 单字节异或
     *
     * @param src1
     * @param src2
     * @return
     */
    public static byte byteXOR(byte src1, byte src2) {
        return (byte) ((src1 & 0xFF) ^ (src2 & 0xFF));
    }

    /**
     * 字节数组异或
     *
     * @param src1
     * @param src2
     * @return
     */
    public static byte[] bytesXOR(byte[] src1, byte[] src2) {
        int length = src1.length;
        if (length != src2.length) {
            return null;
        }
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = byteXOR(src1[i], src2[i]);
        }
        return result;
    }

    /**
     * 字节数组转HEXDECIMAL
     *
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
