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

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author zhoumiaoqian
 * @version $Id$
 * @since 2018年3月15日
 */
@SuppressWarnings({"restriction"})
public class ThreeDES {
    public static final String KEY = "vc1Enlbk9fdm6XKqQSW14dwe";
    private static final String IV = "r1OQ3LOJ";

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {

        // 加密流程
        String telePhone = "{\"state\":\"000000\",\"order\":\"15211056471001\",\"text\":\"\\u6ce8\\u518c\\u6210\\u529f!\",\"id\":{\"0\":103001000005,\"100007\":103001000005,\"100008\":103001000006,\"100009\":103001000005,\"100010\":103001000006}}";
        ThreeDES threeDES = new ThreeDES();
        String telePhone_encrypt = "";
        telePhone_encrypt = threeDES.encryptThreeDESECB(URLEncoder.encode(telePhone, "UTF-8"), KEY, IV);

        telePhone_encrypt = "KtNgVWtoucTRgDsu4I0I46zOxXF1JDBHeJF2dNvA8aHuyQ47HTNGZy8wj6FVrB0JL0KaIRliLKNfqdUr4h3E2B9fAW737ud3DzuHY0EJdpI/4UkrGALIPQ==";
        System.out.println(telePhone_encrypt);// nWRVeJuoCrs8a+Ajn/3S8g==

        // 解密流程
        String tele_decrypt = threeDES.decryptThreeDESECB(telePhone_encrypt, KEY, IV);
        System.out.println("模拟代码解密:" + URLDecoder.decode(tele_decrypt));
    }

    /**
     * DESCBC加密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回加密后的数据
     * @throws Exception
     */
    public String encryptDESCBC(final String src, final String key, String iv) throws Exception {

        // --生成key,同时制定是des还是DESede,两者的key长度要求不同
        final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // --加密向量
        final IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));

        // --通过Chipher执行加密得到的是一个byte的数组,Cipher.getInstance("DES")就是采用ECB模式,cipher.init(Cipher.ENCRYPT_MODE,
        // secretKey)就可以了.
        final Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        final byte[] b = cipher.doFinal(src.getBytes("UTF-8"));

        // --通过base64,将加密数组转换成字符串
        final BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b);
    }

    /**
     * DESCBC解密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回解密后的原始数据
     * @throws Exception
     */
    public String decryptDESCBC(final String src, final String key, String iv) throws Exception {
        // --通过base64,将字符串转成byte数组
        final BASE64Decoder decoder = new BASE64Decoder();
        final byte[] bytesrc = decoder.decodeBuffer(src);

        // --解密的key
        final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // --向量
        final IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));

        // --Chipher对象解密Cipher.getInstance("DES")就是采用ECB模式,cipher.init(Cipher.DECRYPT_MODE,
        // secretKey)就可以了.
        final Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        final byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);

    }

    // 3DESECB加密,key必须是长度大于等于 3*8 = 24 位哈
    public String encryptThreeDESECB(final String src, final String key, String iv) throws Exception {
        final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        final SecretKey securekey = keyFactory.generateSecret(dks);

        final IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey, ivSpec);
        final byte[] b = cipher.doFinal(src.getBytes());

        final BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

    }

    // 3DESECB解密,key必须是长度大于等于 3*8 = 24 位哈
    public String decryptThreeDESECB(final String src, final String key, String iv) throws Exception {
        // --通过base64,将字符串转成byte数组
        final BASE64Decoder decoder = new BASE64Decoder();
        final byte[] bytesrc = decoder.decodeBuffer(src);
        // --解密的key
        final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        final SecretKey securekey = keyFactory.generateSecret(dks);

        // --Chipher对象解密
        final IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, securekey, ivSpec);
        final byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);
    }
}
