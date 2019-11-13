package vip.sunke.common;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author sunke
 * @Date 2018/3/21 11:24
 * @description
 */

public class Base64 {

    private static final byte encodingTable[] = {65, 66, 67, 68, 69, 70, 71,
            72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88,
            89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108,
            109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121,
            122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte decodingTable[];

    static {
        decodingTable = new byte[128];
        for (int i = 65; i <= 90; i++)
            decodingTable[i] = (byte) (i - 65);

        for (int i = 97; i <= 122; i++)
            decodingTable[i] = (byte) ((i - 97) + 26);

        for (int i = 48; i <= 57; i++)
            decodingTable[i] = (byte) ((i - 48) + 52);

        decodingTable[43] = 62;
        decodingTable[47] = 63;
    }

    public static byte[] encode(byte data[]) {
        int modulus = data.length % 3;
        byte bytes[];
        if (modulus == 0)
            bytes = new byte[(4 * data.length) / 3];
        else
            bytes = new byte[4 * (data.length / 3 + 1)];
        int dataLength = data.length - modulus;
        int i = 0;
        for (int j = 0; i < dataLength; j += 4) {
            int a1 = data[i] & 0xff;
            int a2 = data[i + 1] & 0xff;
            int a3 = data[i + 2] & 0xff;
            bytes[j] = encodingTable[a1 >>> 2 & 0x3f];
            bytes[j + 1] = encodingTable[(a1 << 4 | a2 >>> 4) & 0x3f];
            bytes[j + 2] = encodingTable[(a2 << 2 | a3 >>> 6) & 0x3f];
            bytes[j + 3] = encodingTable[a3 & 0x3f];
            i += 3;
        }

        switch (modulus) {
            case 1: {
                int d1 = data[data.length - 1] & 0xff;
                int b1 = d1 >>> 2 & 0x3f;
                int b2 = d1 << 4 & 0x3f;
                bytes[bytes.length - 4] = encodingTable[b1];
                bytes[bytes.length - 3] = encodingTable[b2];
                bytes[bytes.length - 2] = 61;
                bytes[bytes.length - 1] = 61;
                break;
            }
            case 2: {
                int d1 = data[data.length - 2] & 0xff;
                int d2 = data[data.length - 1] & 0xff;
                int b1 = d1 >>> 2 & 0x3f;
                int b2 = (d1 << 4 | d2 >>> 4) & 0x3f;
                int b3 = d2 << 2 & 0x3f;
                bytes[bytes.length - 4] = encodingTable[b1];
                bytes[bytes.length - 3] = encodingTable[b2];
                bytes[bytes.length - 2] = encodingTable[b3];
                bytes[bytes.length - 1] = 61;
                break;
            }
        }
        return bytes;
    }


    //图片转化成base64字符串
    public static String getImageStr(String imgFile) throws Exception {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理

        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    //base64字符串转化成图片
    public static boolean generateImage(String imgStr, String path, String imgFile) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }

            FileUtil.mkdirs(path);

            //生成jpeg图片

            OutputStream out = new FileOutputStream(path + File.separator + imgFile);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
