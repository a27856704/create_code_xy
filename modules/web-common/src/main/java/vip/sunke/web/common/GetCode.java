package vip.sunke.web.common;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GetCode {


    public static String getFixLenthString(int strLength) {
        Random rm = new Random();
        // 获得随机数
        double pross = rm.nextDouble();
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        //	System.out.println(fixLenthString);
        // 返回固定的长度的随机数
        return fixLenthString.substring(2, strLength + 2);
    }

    public static String createRandomNumeric(int length) {

        new RandomStringUtils();
        return RandomStringUtils.randomNumeric(length);

    }


    /**
     * 随机生成码，0-9A-Z
     *
     * @param length 要生成的长度
     * @return
     */

    public static String createRandom(int length) {

        String order = new BigInteger(660, new Random()).toString(36).toUpperCase();
        if (length > order.length())
            return order;
        return order.substring(0, length);

    }


    /**
     * 生成订单号
     *
     * @param length：订单长度
     * @return
     */
    public static String createOrderNo(int length) {

        String orderNo = "";

        if (length <= 14) {
            orderNo = createRandomNumeric(length);
        } else {
            orderNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + createRandomNumeric(length - 14);
        }
        return orderNo;
    }


    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }


    private static String[] telFirst = "188,170,171,134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    public static String getRandomVirtualPhone() {

        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        //	String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String second = SecurityUtil.generateCharacter(4);
        String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + thrid;

    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++)
            System.out.println(getNum(1, 3));

    }


}