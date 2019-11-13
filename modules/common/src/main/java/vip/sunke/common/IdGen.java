package vip.sunke.common;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author sunke
 * @Date 2017/6/12 09:33
 * @description
 */

public class IdGen {


    private static SecureRandom random = new SecureRandom();


    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }


    /**
     * Activiti ID 生成
     */
    public String getNextId() {
        return IdGen.uuid();
    }


}
