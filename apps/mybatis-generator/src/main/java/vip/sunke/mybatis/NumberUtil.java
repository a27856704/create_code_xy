/*
 *
 */

package vip.sunke.mybatis;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数字工具类
 * User: Lucifer
 * Date: 13-6-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class NumberUtil {
    protected static char randomSymbol[] = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    protected static char[] randomNumbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    protected static Random random = new Random();
    private static SecureRandom srandom = new SecureRandom();
    private static AtomicInteger counter = new AtomicInteger(0);


    public static boolean bit(int total, int currentNumber) {

        return (total & currentNumber) == currentNumber;

    }


    public static boolean isNotEmpty(Integer val) {
        if (val != null && val != 0)
            return true;
        return false;
    }

    public static String integertoStr(Integer val) {
        if (val == null)
            return "";
        return val.toString();
    }

    public static String doubletoStr(Double val) {
        if (val == null)
            return "";
        return val.toString();
    }

    /**
     * 返回不小于val的整数
     *
     * @param val 数字
     * @return 不小于val的整数
     */
    public static int ceil(double val) {
        return (int) Math.ceil(val);
    }

    public static String randomString() {
        //return UUID.randomUUID().toString().replaceAll("-","");
        return UUID.randomUUID().getLeastSignificantBits() + "";
    }

    //返回随机文字
    public static String randomString(int length) {
        StringBuffer tmpString = new StringBuffer();
        for (int i = 0; i < length; i++)
            tmpString.append(randomSymbol[random.nextInt(randomSymbol.length)]);
        return tmpString.toString();
    }

    public static double decimalFormat(double value) {
        return decimalFormat("##.00", value);
    }

    public static double decimalFormat(String pattern, double value) {
        DecimalFormat df = new DecimalFormat(pattern);
        return Double.parseDouble(df.format(value));
    }

    /**
     * 格式化2位小数的金额
     *
     * @param value 值
     * @return 格式化2位小数的金额
     */
    public static String formatMoney(Double value) {
        if (value == null) {
            value = 0.00;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    public static String randomNumbers(int length) {
        return Math.abs(srandom.nextLong()) + "";
    }

    public static String randomNums(int length) {
        if (counter.get() > 999999) counter.set(1);
        long time = System.currentTimeMillis();
        String returnValue = time * length + counter.incrementAndGet() + "";
        return returnValue;
    }

    /**
     * 传入double类型元为单位的金额，或者String类型元为单位的金额
     *
     * @param money 金额
     * @return 转成分
     */
    public static int ChangeYuanToFen(Object money) {
        int result = 0;
        if (money instanceof Double)
            result = (int) ((Double) money * 10000 / 100);
        if (money instanceof String)
            result = (int) new BigDecimal(money.toString()).multiply(new BigDecimal("10000")).doubleValue() / 100;
        return result;
    }

    public static Integer plus(Integer oval, Integer nval) {
        return oval + nval;
    }

    public static Integer plus(String oval, Integer nval) {
        return Integer.parseInt(oval) + nval;
    }

    /**
     * 产生一组随机数字 格式 9642-9095-3939-4872
     *
     * @return 产生一组随机数字
     */
    public static String createTicketNo() {
        StringBuffer no = new StringBuffer();
        int n = 9000;
        Set<Integer> mySet = new HashSet<Integer>();
        while (mySet.size() < 4)
            mySet.add((int) (Math.random() * n) + 1000);

        int index = 0;
        for (Integer i : mySet) {
            index++;
            no.append(i);
            if (index < 4)
                no.append("-");
        }
        return no.toString();
    }

    /**
     * int类型数字格式转换
     *
     * @param obj 原始对象
     * @return 转换后int数字
     */
    public static int parseInt(Object obj) {
        return parseInt(obj, 0);
    }

    /**
     * int类型数字格式转换
     *
     * @param obj        原始对象
     * @param defaultVal 默认值
     * @return 转换后int数字
     */
    public static int parseInt(Object obj, int defaultVal) {
        if (obj == null || "".equals(obj)) {
            return defaultVal;
        }
        try {
            return new Integer(obj.toString()).intValue();
        } catch (Exception e) {


            return defaultVal;
        }
    }


    /**
     * long类型数字格式转换
     *
     * @param obj 原始对象
     * @return 转换后long数字
     */
    public static long parseLong(Object obj) {
        return parseLong(obj, 0L);
    }

    /**
     * long类型数字格式转换
     *
     * @param obj        原始对象
     * @param defaultVal 默认值
     * @return 转换后long数字
     */
    public static long parseLong(Object obj, long defaultVal) {
        if (obj == null || "".equals(obj)) {
            return defaultVal;
        }
        try {
            return new Long(obj.toString()).longValue();
        } catch (Exception e) {


            return defaultVal;
        }
    }

    public static boolean isLong(Object obj) {

        try {
            new Long(obj.toString()).longValue();
            return true;

        } catch (Exception e) {


        }
        return false;

    }

}
