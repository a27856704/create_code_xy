package vip.sunke.common;

import java.io.UnsupportedEncodingException;

/**
 * 编码判断辅助类
 *
 * @author Administrator
 */
public class EncodeUtil {
    public static String getEncoding(String str) {
        String encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "UTF-8";
    }

    public static String encodeStr(String str) {
        String encode = getEncoding(str);

        if ("GBK".equals(encode) || "GB2312".equals(encode)) {
            return str;
        }
        try {
            str = new String(str.getBytes(encode), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String encodeStr(String str, String encode) {
        String strEncode = getEncoding(str);
        if (encode.equalsIgnoreCase(strEncode) || encode.equalsIgnoreCase(strEncode)) {
            return str;
        }
        try {
            str = new String(str.getBytes(), encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;


    }


}
