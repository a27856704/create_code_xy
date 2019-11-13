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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


/**
 * @ClassName: AESUtil
 * @Description: 对cookie进行加密解密
 * @date 2015-9-23 上午9:07:18
 */
public class AESUtils {

    private static final String logalrithm = "AES/CBC/PKCS5Padding";
    private static final String bm = "utf-8";

    /**
     * @param msg 加密的数据
     * @return
     * @throws
     * @Title: encrypt
     * @Description: 加密，使用指定数据源生成密钥，使用用户数据作为算法参数进行AES加密
     * @date 2015-9-23 上午9:09:20
     */
    public static String encrypt(String msg, String key, String iv) {
        byte[] encryptedData = null;
        try {
            byte[] keyValue = key.getBytes(bm);
            Key keySpec = new SecretKeySpec(keyValue, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(bm));
            Cipher ecipher = Cipher.getInstance(logalrithm);
            ecipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            encryptedData = ecipher.doFinal(msg.getBytes(bm));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return asHex(encryptedData);
    }


    /**
     * @param value
     * @return
     * @throws
     * @Title: decrypt
     * @Description: 解密，对生成的16进制的字符串进行解密
     * @date 2015-9-23 上午9:10:01
     */
    public static String decrypt(String value, String key, String iv) {
        try {
            Key keySpec = new SecretKeySpec(key.getBytes(bm), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(bm));
            Cipher ecipher = Cipher.getInstance(logalrithm);
            ecipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(ecipher.doFinal(asBin(value)));
        } catch (UnsupportedEncodingException e) {
            System.out.println("不支持次方法：" + value);
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.out.println("解密错误：" + value);
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.out.println("解密错误：" + value);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @param buf
     * @return
     * @throws
     * @Title: asHex
     * @Description: 将字节数组转换成16进制字符串
     * @date 2015-9-23 上午9:10:25
     */
    private static String asHex(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }


    /**
     * @param src
     * @return
     * @throws
     * @Title: asBin
     * @Description: 将16进制字符串转换成字节数组
     * @date 2015-9-23 上午9:10:52
     */
    private static byte[] asBin(String src) {
        if (src.length() < 1) {
            return null;
        }
        byte[] encrypted = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

    public static void main(String[] args) {
        final String key = "AXDVGNJL%^&*)(!@";
        final String iv = "!@ab()cd&^ef%$hi";
        String userid = "897807300@qq.com";
        System.out.println(encrypt(userid, key, iv));
        System.out.println(decrypt(encrypt(userid, key, iv), key, iv));
        System.out.println(decrypt("95d6fdd35f5ea2b7969ef3e6bd1e118dbf31e0abe876325d6a0a5456ae3c0c8b", key, iv));
    }

}