package ${pubPackage}.pubInter.exception;

/**
* @author ${author}
* @Date ${createTime}
* @description DAO异常枚举
*/

public interface ExceptionEnum<eEnum>{


    int getCode();

    String getKey();

    String getMessage();


    eEnum getEnum(int code);



}
