package vip.sunke.common;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends TextUtils {



    public static String isNullToDefault(String obj,String defaultV){
        if(StringUtil.isEmpty(obj))
            return defaultV;
        return obj;


    }


    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || "".equals(obj.toString());
    }

    public static String toString(Object obj) {
        if (obj == null)
            return "";
        return obj.toString();
    }

    public static String toString(Object obj, String defaultStr) {
        if (obj == null || "".equals(obj.toString().trim()))
            return defaultStr;
        return obj.toString();
    }


    public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }

    /**
     * 字符替换 %s
     *
     * @param str
     * @param objArr
     * @return
     */
    @SuppressWarnings("static-access")
    public static String format(String str, Object[] objArr) {
        if (str == null)
            return "";
        return String.format(str, objArr);
    }

    public static String format(String str, Object arg) {
        return format(str, new Object[]{arg});
    }

    public static String format(String str, Object arg, Object arg2) {
        return format(str, new Object[]{arg, arg2});
    }

    public static String format(String str, Object arg, Object arg2, Object arg3) {
        return format(str, new Object[]{arg, arg2, arg3});
    }

    public static String format(String str, Object arg, Object arg2,
                                Object arg3, Object arg4) {
        return format(str, new Object[]{arg, arg2, arg3, arg4});
    }

    /**
     * 把null值替换为空
     *
     * @param text
     * @return
     */
    public static String formatString(String text) {
        String str = (text == null || "null".equals(text)) ? "" : text.trim();
        return str;
    }

    /**
     * 截取字符
     *
     * @param text     截取的原字符
     * @param partSize 截取的长度
     * @param begin    是否开头截取 true 是 false 从后面开始
     * @return
     */

    public static String getPartString(String text, int partSize, boolean begin) {
        if (text == null || "".equals(text))
            return "";
        int len = text.length();

        if (partSize >= len)
            return text;
        if (begin)
            return text.substring(0, partSize);
        return text.substring(len - partSize, len);

    }


    public static String replaceStr(int indexStart, String res,
                                    String replaceStr) {

        if (res == null)
            return "";

        return StringUtil.replaceStr(indexStart, res.length(), res, replaceStr);


    }

    public static String formatTruename(String truaname) {


        return StringUtil.replaceStr(1, truaname, "*");

    }


    public static String formatBank(String bankString) {
        if (bankString == null || "".equals(bankString) || bankString.length() < 8)
            return bankString;


        String bank = StringUtil.replaceStr(4, bankString.length() - 3, bankString, "*");

        return bank;

    }

    public static String formatIdcard(String cardString) {
        if (cardString == null || "".equals(cardString) || cardString.length() < 7)
            return cardString;


        String bank = StringUtil.replaceStr(4, cardString.length() - 2, cardString, "*");

        return bank;

    }


    public static String formatPhone(String phone) {

        return StringUtil.replaceStr(3, 7, phone, "*");

    }

    public static String getPhoneLastNumber(String phone, int lastNum) {

        return StringUtil.replaceStr(0, (phone.length() - lastNum), phone, "");

    }


    /**
     * @param indexStart 开始位置 0开始算
     * @param indexEnd   结束位置
     * @param res        原字符
     * @param replaceStr 换成字符
     * @return
     */

    public static String replaceStr(int indexStart, int indexEnd, String res,
                                    String replaceStr) {

        try {

            int size = res.length();

            if (indexEnd > size)
                indexEnd = size;
            if (indexStart > size)
                return res;
            String temp = res.substring(0, indexStart);
            for (int i = indexStart; i < indexEnd; i++) {
                temp = temp + replaceStr;
            }
            if (indexEnd < size)
                temp = temp + res.substring(indexEnd);
            return temp;
        } catch (Exception e) {

        }

        return res;

    }

    public static String createRandomNumeric(int length) {

        if (length == 0)
            return "";
        new RandomStringUtils();
        return RandomStringUtils.randomNumeric(length);

    }


    /**
     * 生成合同编号
     *
     * @param sourceFlag
     * @param date
     * @param tenderId
     * @return
     */

    public static String createContractNum(int sourceFlag, Date date,
                                           int tenderId) {

        String prefix = "BD";
        if (sourceFlag == 1) {// 标
            prefix = "SBTZ";
        } else if (sourceFlag == 2) {// 理财
            prefix = "DQLC";
        } else if (sourceFlag == 3) {// 债权
            prefix = "ZQZR";
        }
        String num = "";
        if (tenderId < 10) {
            num = "00" + tenderId;
        } else if (tenderId < 100) {
            num = "0" + tenderId;
        } else {
            num = tenderId + "";
        }

        return prefix + YXDate.getFormatDate2String(date, "yyyyMMdd") + num;

    }

    /**
     * 生成订单号
     *
     * @param length ：订单长度
     * @return
     */
    public static String createOrderNo(int length) {

        String orderNo = "";

        if (length < 14) {
            orderNo = createRandomNumeric(length);
        } else {
            orderNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    + createRandomNumeric(length - 14);
        }
        return orderNo;
    }


    private static String encryptDateStr(String dateStr) {
        if (dateStr == null || "".equals(dateStr))
            return "";


        String result = "";
        int i = 0;
        for (int k = 0; k < dateStr.length(); k++) {
            i = new Integer(dateStr.substring(k, k + 1)).intValue();
            result = result + ((i + 7) % 9);
        }

        return result;

    }

    /**
     * 生成订单号
     *
     * @param prefix    前缀
     * @param randomLen 随机的长度
     * @return
     */
    public static String createOrderNo(String prefix, int randomLen, String formatStr) {

        String dateStr = YXDate.getFormatDate2String(new Date(), formatStr);
        return prefix + (dateStr) + createRandomNumeric(randomLen);

    }


    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script,
                Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern
                .compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
        htmlStr = htmlStr.replaceAll("&nbsp;", "");

        return htmlStr.trim(); // 返回文本字符串
    }

    public static String subHtmlString(String htmlStr, int len) {
        String html = delHTMLTag(htmlStr);
        if (html.length() < len)
            return html;
        return html.substring(0, len);


    }


    /**
     * 字符转整形
     *
     * @param str
     * @param defalut
     * @return
     */

    public static int strToInt(String str, int defalut) {

        if (str == null)
            return defalut;
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {

        }
        return defalut;

    }

    /**
     * 字符转长整形
     *
     * @param str
     * @param defalut
     * @return
     */

    public static long strToLong(String str, long defalut) {

        if (str == null)
            return defalut;
        try {
            return Long.parseLong(str);
        } catch (Exception e) {

        }
        return defalut;

    }


    /**
     * 手机号码检测
     *
     * @param phone
     * @return
     */

    public static boolean isMobileNO(String phone) {

        if (phone == null || "".equals(phone))
            return false;

        Pattern p = Pattern.compile("^1[3-9]+\\d{9}$");

        Matcher m = p.matcher(phone);

        return m.matches();

    }

    public static boolean isEmail(String email) {

        if (email == null || "".equals(email))
            return false;

        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }


    public static String toStringByObj(Object object, String defaultStr) {
        if (object == null)
            return defaultStr;
        return object.toString();


    }

    public static <W> W[] addNewItemToArray(W[] arrs, W item, Class<W> clazz) {

        if (arrs == null)
            return null;

        List<W> list = new ArrayList<W>();
        for (int i = 0; i < arrs.length; i++) {
            list.add(arrs[i]);
        }

        list.add(item);


        W[] returnArr = (W[]) Array.newInstance(clazz, list.size());

        list.toArray(returnArr);

        return returnArr;


    }

    public static String strArrayToString(String[] arr) {

        return strArrayToString(arr, ",");
    }


    public static String strArrayToString(String[] arr, String joinStr) {


        if (arr == null || arr.length == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            sb.append(joinStr);
        }
        String resultStr = sb.toString();
        return resultStr.substring(0, resultStr.length() - 1);


    }

    public static List<Object> setPackage(Object... objects) {
        List<Object> results = new ArrayList<Object>();
        for (Object o : objects) {
            results.add(o);
        }

        return results;

    }

    public static void main(String[] args) {

        System.out.println(unicodeToString("dafsdfa"));
    }


    /**
     * 反斜杠变成斜杠
     *
     * @param str
     * @return
     */
    public static String swayStr(String str) {

        if (str == null || "".equals(str))
            return "";

        return str.replaceAll("\\\\", "/");


    }

    /**
     * 完整的判断中文汉字和符号
     *
     * @param strName
     * @return
     */
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;

    }


    public static String unicodeToString(String str) {
        try {
            Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
            Matcher matcher = pattern.matcher(str);
            char ch;
            while (matcher.find()) {
                //group 6728
                String group = matcher.group(2);
                //ch:'木' 26408
                ch = (char) Integer.parseInt(group, 16);
                //group1 \u6728
                String group1 = matcher.group(1);
                str = str.replace(group1, ch + "");
            }
            return str;
        } catch (Exception e) {
            return str;
        }
    }


    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {

            return true;
        }
        return false;

    }

    /**
     * 首字母大写
     *
     * @param name String 原始字母字符串
     * @return 首字母大写之后的字符串
     */
    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);

    }


    /**
     * Consider whether a string is null or empty
     *
     * @param aStr A string 字符串
     * @return bool  is null or empty
     */
    public static final boolean isEmpty(String aStr) {
        return aStr == null || aStr.trim().equals("") || aStr.trim().equalsIgnoreCase("null");
    }


    /**
     * Consider whether a string has content
     *
     * @param aStr A string
     * @return a string has content
     */
    public static final boolean isNotEmpty(String aStr) {
        return !isEmpty(aStr);
    }


    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 判断2个字符是否相等
     *
     * @param aStr aStr
     * @param bStr bStr
     * @return true
     */
    public static boolean equal(String aStr, String bStr) {
        if (isEmpty(aStr) || isEmpty(bStr))
            return false;
        return aStr.equals(bStr);
    }

    /**
     * Convert a string's /t,/n,' ' into html support characters
     *
     * @param aStr astr
     * @return Convert   html support characters string
     */
    public static final String convert2HTML(String aStr) {
        if (isNotEmpty(aStr)) {
            aStr = aStr.replace("\n", "<br/>");
            //aStr = aStr.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
            //aStr = aStr.replace(" ", "&nbsp;");
        }
        return aStr;
    }

    public static String left(String source, char padding, int len) {
        if (source.length() >= len) {
            return source;
        }

        while (source.length() < len) {
            source = padding + source;
        }
        return source;
    }

    /**
     * Join strings into one
     *
     * @param strings Strins to join
     * @return Join strings into one
     */
    public static final String joinString(String... strings) {
        StringBuffer joinOne = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            if (StringUtil.isNotEmpty(strings[i])) {
                joinOne.append(strings[i]);
            }
        }
        return joinOne.toString();
    }

    /**
     * clean empty or null characters
     *
     * @param aStr aStr
     * @return clean empty or null characters
     */
    public static final String clean(String aStr) {
        if (aStr == null || aStr.equals("null")) {
            return "";
        }
        return aStr.trim();
    }

    /**
     * toScriptUnicode的默认方法
     *
     * @param strText 待转换的字符串
     * @return 转换后的Unicode码字符串
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static String toScriptUnicode(String strText) throws UnsupportedEncodingException {
        return toScriptUnicode(strText, System.getProperty("file.encoding"));
    }

    /**
     * 把字符串转换成Unicode码,并且可以由javascript的String.fromCharCode()解释
     *
     * @param strText 待转换的字符串
     * @param code    转换前字符串的编码，如"GBK"
     * @return 转换后的Unicode码字符串
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static String toScriptUnicode(String strText, String code) throws UnsupportedEncodingException {
        char c;
        StringBuffer strRet = new StringBuffer();
        try {
            strText = new String(strText.getBytes(), code);
            for (int i = 0; i < strText.length(); i++) {
                c = strText.charAt(i);
                Integer charCode = new Integer(c);
                strRet.append(charCode.toString());
                if (i < strText.length() - 1) {
                    strRet.append(",");
                }
            }
            return strRet.toString();
        } finally {
            strRet = null;
        }
    }

    /**
     * 将一个字符串转换成一个数组。
     * <p>比如：IvlP --&gt; String[]{"I","v","l","p"}</p>
     *
     * @param str 字符串
     * @return 数组
     */
    public static String[] convertToArray(String str) {
        int len = str.length();
        String[] result = new String[len];
        for (int i = 0; i < len; i++) {
            result[i] = str.substring(i, i + 1);
        }
        return result;
    }

    /**
     * 将字符串数组转换成一个字符串，用分隔串分开。
     * <pre>例如：
     * new String[]{"hello","pengpeng5",".com"}  --转换成--&gt;   "hello,pengpeng5,.com"
     * </pre>
     *
     * @param stringArray 字符串数组
     * @param seperator   分隔符
     * @return 如果数组为空，那么返回空字符串
     */
    public static String convertStringArray2String(String[] stringArray, String seperator) {
        if (null == stringArray) {
            return "";
        }
        if (StringUtil.isEmpty(seperator)) {
            return String.valueOf(stringArray);
        }
        StringBuffer bookstr = new StringBuffer();

        final int COUNT = stringArray.length;
        for (int i = 0; i < COUNT; i++) {
            bookstr.append(stringArray[i]);
            if (i < COUNT - 1) {
                bookstr.append(seperator);
            }
        }

        return bookstr.toString();
    }

    /**
     * 将字符串数组转换成一个字符串，用分隔串分开。
     * <pre>例如：
     * new Integer[]{2,4,6}  --转换成--&gt;   "2,4,6"
     * </pre>
     *
     * @param integerArray 数值数组
     * @param seperator    分隔符
     * @return 如果数组为空，那么返回空字符串
     */
    public static String convertIntegerArray2String(Integer[] integerArray, String seperator) {
        StringBuffer bookstr = new StringBuffer();

        final int COUNT = integerArray.length;
        for (int i = 0; i < COUNT; i++) {
            bookstr.append(integerArray[i]);
            if (i < COUNT - 1) {
                bookstr.append(seperator);
            }
        }

        return bookstr.toString();
    }

    /**
     * <ol>
     * <li> 替换空格符号为空字符串</li>
     * <li> 替换硬回车符号为空字符串</li>
     * <li> 替换软回车符号为空字符串</li>
     * </ol>
     *
     * @param description 描述
     * @return 返回替换之后的字符串
     */
    public static String replaceSpecialNotation(String description) {
        if (null == description || "".equals(description)) {
            return description;
        }
        String spacePattern = " "; //空格符号
        String hardEnterPattern = "\r";//硬回车符号
        String softEnterPattern = "\n";//软回车符号
        String replaceStr = "";

        //替换空格
        Pattern pattern = Pattern.compile(spacePattern);
        Matcher matcher = pattern.matcher(description);
        description = matcher.replaceAll(replaceStr);

        //替换硬回车符号
        pattern = Pattern.compile(hardEnterPattern);
        matcher = pattern.matcher(description);
        description = matcher.replaceAll(replaceStr);

        //替换软回车符号
        pattern = Pattern.compile(softEnterPattern);
        matcher = pattern.matcher(description);
        description = matcher.replaceAll(replaceStr);
        return description;
    }

    /**
     * <ol>
     * <li> 替换硬回车符号为空字符串</li>
     * <li> 替换软回车符号为空字符串</li>
     * </ol>
     *
     * @param description 描述
     * @return 替换后的字符串
     */
    public static String replaceSpecialNotationNoBlank(String description) {
        if (null == description || "".equals(description)) {
            return description;
        }
        String hardEnterPattern = "\r";//硬回车符号
        String softEnterPattern = "\n";//软回车符号
        String replaceStr = "";

        //替换硬回车符号
        Pattern pattern = Pattern.compile(hardEnterPattern);
        Matcher matcher = pattern.matcher(description);
        description = matcher.replaceAll(replaceStr);

        //替换软回车符号
        pattern = Pattern.compile(softEnterPattern);
        matcher = pattern.matcher(description);
        description = matcher.replaceAll(replaceStr);
        return description;
    }

    /**
     * 这个工具方法将字符串数组转换成数值数值，非数值的字符串将被忽略。
     *
     * @param strings 字符串数组
     * @return 数值数组
     */
    public static Integer[] convertStringArray2Integer(String[] strings) {
        if (strings == null) {
            return null;
        }
        List<Integer> integers = new ArrayList<Integer>();
        for (int i = 0; i < strings.length; i++) {
            try {
                Integer integer = Integer.parseInt(strings[i]);
                integers.add(integer);
            } catch (Exception e) {
                //不需要处理这个异常
            }
        }

        Integer[] newArray = new Integer[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            newArray[i] = integers.get(i);
        }
        return newArray;
    }

    /**
     * 将字符串转成数组，如果字符串为 null 或者为空字符串，那么返回 null.
     *
     * @param str       字符串
     * @param seperator 分隔符
     * @return 将字符串转成数组，如果字符串为 null 或者为空字符串，那么返回 null.
     */
    public static String[] convertString2Array(String str, String seperator) {
        if (str == null || "".equals(str)) {
            return null;
        }
        return StringUtils.split(str, seperator);
    }


    /**
     * 将字符串中的变量进行替换.
     * <p>变量格式： ${VAR_NAME}</p>
     * <p>HashMap 存储的是变量名和相应的值。</p>
     * <code>
     * String original = &quot;${CONTEXT_URI_RESOURCE_CSS}/global/${THEME_CSS}&quot;;<br>
     * HashMap&lt;String,String&gt; variables = new HashMap&lt;String,String&gt;();<br>
     * variables.put(&quot;CONTEXT_URI_RESOURCE_CSS&quot;,&quot;http://res.binguo.com/css&quot;);<br>
     * variables.put(&quot;THEME_CSS&quot;,&quot;theme.css&quot;);<br>
     * original = StringUtil.replaceVariables(original,variables);<br>
     * System.out.println(original);
     * </code>
     * <p>输出：</p>
     * <p>http://res.binguo.com/css/global/theme.css</p>
     *
     * @param original  字符串
     * @param variables 变量对应表
     * @return 将字符串中的变量进行替换. 变量格式： ${VAR_NAME} HashMap 存储的是变量名和相应的值。
     */
    public static String replaceVariables(String original, HashMap<String, String> variables) {
        if (StringUtils.hasLength(original)) {
            return original;
        }

        try {
            Set key = variables.keySet();
            for (Object _k : key) {
                String k = (String) _k;
                String v = variables.get(k);
                original = original.replaceAll("\\$\\{" + k + "\\}", v);
            }
        } catch (Exception e) {
        }
        return original;
    }

    public static String toLowerCase(String str) {
        if (str.equals("") || str.length() <= 0) {
            return "";
        }

        return str.toLowerCase();
    }

    /**
     * 截取指定字节数文本
     *
     * @param value        值
     * @param requireBytes 字节数
     * @return 返回截取字符串
     */
    public static String getBytesLengthGBK(String value, int requireBytes) {
        String result;
        if (value == null) return "";
        if (value.length() < requireBytes / 2) return value;

        try {
            if (value.length() > requireBytes)
                result = value.substring(0, requireBytes);
            else
                result = value;

            byte[] bytes = result.getBytes("GBK");
            if (bytes.length <= requireBytes) {
            } else {
                byte[] newBytes = new byte[requireBytes];
                System.arraycopy(bytes, 0, newBytes, 0, requireBytes);
                result = new String(newBytes, "GBK");
                if (result.charAt(result.length() - 1) == (char) 65533) {
                    result = result.substring(0, result.length() - 1);
                }
            }

        } catch (Exception ex) {
            return value;
        }
        return result;
    }

    /**
     * 截取指定中文字数文本
     *
     * @param value         值
     * @param requireLength 字数（中文）
     * @return 截取字数文本
     */
    public static String cutTextGBK(String value, int requireLength) {
        return getBytesLengthGBK(value, requireLength * 2);
    }

    /*public static boolean isBlankOrEmpty(String str) {
        return StringUtils.isBlank(str) || StringUtils.isEmpty(str);
    }*/

    /**
     * 得到文本的真正长度
     *
     * @param str 文本
     * @return 文本长度
     */
    public static int getRealLength(String str) {
        int length = 0;
        if (str != null) {
            length = str.getBytes().length;
        }
        return length;
    }

    /**
     * 小写字符串首字符.
     *
     * @param str 字符串
     * @return 小写字符串首字符
     */
    public static String toLowercaseStrHead(String str) {
        if (str == null) {
            return null;
        }

        if (str.length() > 0) {
            return str.substring(0, 1).toLowerCase() + (str.length() > 1 ? str.substring(1) : "");
        } else {
            return "";
        }
    }

    /**
     * 切割文本分行显示（超出部分不显示）
     *
     * @param text    文本
     * @param rowSize 每行字数（按中文字）
     * @param rowNum  行数
     * @return
     */
    /*public static String splitTextHtml(String text, int rowSize, int rowNum) {
        if (rowNum < 1) {
            rowNum = 1;
        }
        String workText = new String(text);
        StringBuffer textSB = new StringBuffer("");
        for (int i = 1; i <= rowNum && !isBlankOrEmpty(workText); i++) {
            String partText = cutTextGBK(workText, rowSize);
            if (!isBlankOrEmpty(partText)) {
                if (i > 1) {
                    textSB.append("<br>");
                }
                textSB.append(partText);
                workText = workText.substring(partText.length());
            } else {
                break;
            }
        }
        return textSB.toString();
    }*/

    /**
     * 删除字符串中某个元素(字符串的格式:'2,3,4',元素与元素之间用','逗号隔开)
     *
     * @param str  字符串
     * @param item 某个元素
     * @return 删除字符串中某个元素
     */
    public static String deleteStringItem(String str, String item) {
        str = str.replaceAll(item, "");
        str = str.replaceAll(",,", ",");
        if (str.startsWith(",")) {
            str = str.substring(1, str.length());
        }
        if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 字符串替换，全部替换
     *
     * @param from   从
     * @param origin 原始
     * @param to     到
     * @return 替换后的值
     */
    public static String replaceAll(String origin, String from, String to) {
        if (origin == null || origin.length() == 0 || from == null || to == null || origin.indexOf(from) < 0)
            return origin;
        StringBuffer sb = new StringBuffer();
        int k = 0;
        int idx = 0;
        while (true) {
            idx = origin.indexOf(from, k);
            if (idx < 0) break;
            sb.append(origin.substring(k, idx));
            sb.append(to);
            k = idx + from.length();
        }
        sb.append(origin.substring(k, origin.length()));
        return sb.toString();
    }

    /**
     * 获取 数组中不为空的值
     *
     * @param param 数组参数
     * @return 数组数量
     */
    public static int countString(String[] param) {
        int i = 0;
        for (String val : param) {
            if (StringUtil.isNotEmpty(val)) {
                i++;
            }
        }
        return i;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素&gt;0)
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isNotObjEmpty(Object pObj) {
        if (pObj == null)
            return false;
        if (pObj == "")
            return false;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return false;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return false;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isObjEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if (pObj == "")
            return true;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得字符(包含中文)长度.
     *
     * @param str
     * @return
     */
    /*public static int getChineseLength(String str) {
        if (StringUtils.isBlank(str)) {
            return 0;

        }

        try {
            return str.getBytes("gbk").length;
        } catch (UnsupportedEncodingException e) {
            logger.error("<getChineseLength> ", e);
        }
        return 0;
    }*/

    /**
     * 传入对象 输出 toString 以键值对形式
     *
     * @param o 对象
     * @return toString 以键值对形式
     */
    public static String loggerString(Object o) {
        return ToStringBuilder.reflectionToString(o);
    }

    /**
     * BufferedReader转换成String
     * 注意:流关闭需要自行处理
     *
     * @param reader BufferedReader
     * @return String 字符串
     * @throws IOException io异常
     */
    public static String bufferedReader2String(BufferedReader reader) throws IOException {
        StringBuffer buf = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            buf.append(line);
            buf.append("\r\n");
        }

        return buf.toString();
    }

}
