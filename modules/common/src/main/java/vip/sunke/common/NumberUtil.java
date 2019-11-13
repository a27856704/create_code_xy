package vip.sunke.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理数字的公共方法
 *
 * @author Jason
 */
public final class NumberUtil {


    public static boolean bitwise(int one, int two) {
        return (one & two) == two;

    }


    /**
     * 双精度数字格式转换
     *
     * @param obj 原始对象
     * @return 转换后双精度数字
     */
    public static double parseDouble(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        }
        try {
            return new Double(obj.toString()).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 双精度数字格式转换
     *
     * @param obj 原始对象
     * @return 转换后双精度数字
     */
    public static double parseDouble(Object obj, double defaultVal) {
        if (obj == null || "".equals(obj)) {
            return 0;
        }
        try {

            return new Double(obj.toString()).doubleValue();
        } catch (Exception e) {
            return defaultVal;
        }
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

    public static long parseLong(Object obj) {

        return parseLong(obj, 0);
    }


    /**
     * float  四舍五入,保留位数
     *
     * @param objFloat
     * @param scale    保留小数点几位
     * @return
     */
    public static float parseFloat(float objFloat, int scale) {


        try {
            BigDecimal b = new BigDecimal(objFloat);
            return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return objFloat;

    }

    /**
     * 单精度类型数字格式转换
     *
     * @param obj 原始对象
     * @return 转换后单精度数字
     */
    public static float parseFloat(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        }
        try {
            return new Float(obj.toString()).floatValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public static String formatBigDecimal(BigDecimal b) {
        if (b == null)
            return "0";
//		NumberFormat nf = new DecimalFormat("###.##");
//		return nf.format(b);

        return b.toString();
    }


    public static int compareTo(BigDecimal one, BigDecimal two) {
        if (one == null)
            one = BigDecimal.ZERO;
        if (two == null)
            two = BigDecimal.ZERO;

        return one.compareTo(two);

    }


    public static BigDecimal dealBigDecimal(Object b) {
        return dealBigDecimal(b, BigDecimal.ZERO);
    }

    public static BigDecimal dealBigDecimal(Object b, BigDecimal defaultVal) {
        if (b == null || "".equals(b)) {
            return defaultVal;
        }
        try {
            return new BigDecimal(b.toString());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return defaultVal;
    }

    /**
     * Fortmat BigDecimal while the number is too long. for example: the param
     * value = 1222222.222222, and then the return will be 1,222,222.22 . It
     * depends on NumberFormat.getInstance() .
     *
     * @param value
     * @return
     */
    public static String formatValue(Object value) {
        try {
            NumberFormat formatter = new DecimalFormat("#,###,###.##");
            value = dealBigDecimal(value);
            // System.out.println(value);
            return formatter.format(value);
        } catch (Exception e) {
            // e.printStackTrace();
            return "0";
        }
    }


    /**
     * 判断是否整数
     *
     * @param money
     * @return
     */
    public static boolean bigDecimalIsInteger(BigDecimal money) {

        if (money == null)
            return false;
        return new BigDecimal(money.intValue()).compareTo(money) == 0;

    }


    public static BigDecimal addBigDecimal(BigDecimal one, BigDecimal two) {
        if (one == null)
            one = BigDecimal.ZERO;
        if (two == null)
            two = BigDecimal.ZERO;

        return one.add(two).setScale(2, 4);
    }


    /**
     * 相减
     *
     * @param one
     * @param two
     * @return
     */
    public static BigDecimal subtractBigDecimal(BigDecimal one, BigDecimal two) {

        if (one == null)
            one = BigDecimal.ZERO;
        if (two == null)
            two = BigDecimal.ZERO;

        return one.subtract(two).setScale(2, 4);

    }


    /**
     * 相除
     *
     * @param one
     * @param two
     * @return
     */

    public static BigDecimal divideBigDecimal(BigDecimal one, BigDecimal two) {

        if (one == null)
            return BigDecimal.ZERO;
        if (two == null)
            return BigDecimal.ZERO;
        return one.divide(two, 2, 4);

    }


    /**
     * 相乘
     *
     * @param one
     * @param two
     * @return
     */
    public static BigDecimal multiply(BigDecimal one, BigDecimal two) {
        if (one == null)
            return BigDecimal.ZERO;
        if (two == null)
            return BigDecimal.ZERO;

        return one.multiply(two).setScale(2, 4);

    }

    public static long[] addNewItemToArray(long[] arrs, long item) {

        if (arrs == null)
            return null;

        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < arrs.length; i++) {
            list.add(arrs[i]);
        }

        list.add(item);


        long[] returnArr = new long[list.size()];

        for (int i = 0; i < list.size(); i++) {
            returnArr[i] = list.get(i).longValue();
        }

        return returnArr;
    }


    public static void main(String[] agrs) {

        System.out.println(parseInt(new BigDecimal("3.21"), 0));

    }

	/*
     * public static void main(String[] args) { // The 0 symbol shows a digit or
	 * 0 if no digit present NumberFormat formatter = new DecimalFormat(
	 * "000000 "); String s = formatter.format(-1234.567); // -001235 // notice
	 * that the number was rounded up
	 * 
	 * // The # symbol shows a digit or nothing if no digit present formatter =
	 * new DecimalFormat( "## "); s = formatter.format(-1234.567); // -1235 s =
	 * formatter.format(0); // 0 formatter = new DecimalFormat( "##00 "); s =
	 * formatter.format(0); // 00
	 * 
	 * 
	 * // The . symbol indicates the decimal point formatter = new
	 * DecimalFormat( ".00 "); s = formatter.format(-.567); // -.57 formatter =
	 * new DecimalFormat( "0.00 "); s = formatter.format(-.567); // -0.57
	 * formatter = new DecimalFormat( "#.# "); s = formatter.format(-1234.567);
	 * // -1234.6 formatter = new DecimalFormat( "#.###### "); s =
	 * formatter.format(-1234.567); // -1234.567 formatter = new DecimalFormat(
	 * ".###### "); s = formatter.format(-1234.567); // -1234.567 formatter =
	 * new DecimalFormat( "#.000000 "); s = formatter.format(-1234.567); //
	 * -1234.567000
	 * 
	 * 
	 * // The , symbol is used to group numbers formatter = new DecimalFormat(
	 * "#,###,### "); s = formatter.format(-1234.567); // -1,235 s =
	 * formatter.format(-1234567.890); // -1,234,568 System.out.println(s);
	 * 
	 * // The ; symbol is used to specify an alternate pattern for negative
	 * values formatter = new DecimalFormat( "#;(#) "); s =
	 * formatter.format(-1234.567); // (1235)
	 * 
	 * // The ' symbol is used to quote literal symbols formatter = new
	 * DecimalFormat( " '# '# "); s = formatter.format(-1234.567); // -#1235
	 * formatter = new DecimalFormat( " 'abc '# "); s =
	 * formatter.format(-1234.567); // -abc1235 System.out.println(s);
	 * 
	 * }
	 */

}
