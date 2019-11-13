/* =======================================================================================================
 *              <<Branchitech>> Software License v1.0
 * =======================================================================================================

 * 版权所有  (c) 2005 <<Branchitech>>

 * Copyright (c) 2005 <<Branchitech>> All rights reserved.

 * <<Branchitech>> 对由其自主开发或和他人共同开发的所有内容和服务拥有全部知识产权，

 * 此等知识产权受到适用的知识产权（版权-著作权、商标权、专利权、非专利技术等）法律

 * 和其他法律及相关国际条约的保护。

 * 未经授权许可，任何个人、单位、组织等不得对本公司的软件产品、程序（包括各种可执行文件、源码、其他文件、

 * 开发文档、技术手册等）进行编译、反编译、再编译、出售、复制、传播、破坏、仿制、非法占有等。

 * 本公司保留一切追究侵犯人法律责任之权利。其他语言之版权声明以此汉语版为准。

 * www.<<Branchitech>>.com

 * ======================================================================================================
 */
package vip.sunke.common;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zhoumiaoqian
 * @version $Id$
 * @since 2017-5-23
 */
public class DES {

    private DES() {
    }

    /**
     * 加密
     *
     * @param src      byte[] 加密的数据源
     * @param password byte[] 加密秘钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] src, byte[] password, byte[] ivbts) {
        try {
            DESKeySpec desKey = new DESKeySpec(password);
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            //ECB模式下，iv不需要
            IvParameterSpec iv = new IvParameterSpec(ivbts);
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(src);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param src      byte[] 加密的数据源
     * @param password String 加密秘钥
     * @param iv       向量字符串，只支持8位字符串
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] src, String password, String iv) {
        byte[] keyBytes = password.getBytes(Charset.forName("UTF-8"));
        byte[] ivbts = iv.getBytes(Charset.forName("UTF-8"));
        return encrypt(src, keyBytes, ivbts);
    }

    /**
     * Java代码 Convert byte[] to hex
     * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 解密
     *
     * @param src      byte[] 解密的数据源
     * @param password byte[] 解密秘钥
     * @param ivbts    向量
     * @return byte[] 解密后的数据
     */
    public static byte[] decrypt(byte[] src, byte[] password, byte[] ivbts) {
        try {
            // DES算法要求有一个可信任的随机数源
            //SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(password);
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作，CBC为加密模式，
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            //ECB模式下，iv不需要
            IvParameterSpec iv = new IvParameterSpec(ivbts);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
            // 真正开始解密操作
            return cipher.doFinal(src);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src        byte[] 解密的数据源
     * @param password   String 解密秘钥
     * @param psdCharset 秘钥字符编码
     * @return byte[] 解密后的数据
     */
    public static byte[] decrypt(byte[] src, String password, String iv, Charset psdCharset) {
        byte[] keyBytes = password.getBytes(psdCharset);
        byte[] ivbts = iv.getBytes(psdCharset);
        return decrypt(src, keyBytes, ivbts);
    }

    /**
     * 解密
     *
     * @param src      byte[] 解密的数据源
     * @param password String 解密秘钥
     * @return byte[] 解密后的数据
     */
    public static byte[] decrypt(byte[] src, String password, String iv) {
        return decrypt(src, password, iv, Charset.forName("UTF-8"));
    }

    public static void main(String[] args) throws Exception {
        final String src = "张三----1adsadsadas";
        final String password = "acbs#()!1dasdadadad";
        final String iv = ")(*a#&^%";
        final String encoder = bytesToHexString(encrypt(src.getBytes("UTF-8"), password, iv));
        System.out.println("encoder->" + encoder);
//    	final String encoder = "ca85593bbfb793c6a6c9a451269027155033c7e55774f212";
        final String decoder = new String(decrypt(hexStringToBytes(encoder), password, iv), "UTF-8");
        System.out.println("decoder->" + decoder);
    }
}