package vip.sunke.pubInter.exception;

/**
 * @author sunke
 * @Date 2017/6/13 15:53
 * @description
 */

public interface ExceptionEnum<eEnum>{


    int getCode();

    String getKey();

    String getMessage();


    eEnum getEnum(int code);



}
