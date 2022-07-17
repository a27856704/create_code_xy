package ${pubPackage}.pubInter.exception;

import java.lang.reflect.ParameterizedType;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public interface ExceptionEnum<eEnum extends ExceptionEnum>{


    int getCode();

    String getKey();

    String getMessage();


    default Class<eEnum> getEnumClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }


    default eEnum getEnum(int code) {
        for (eEnum exceptionEnum : getEnumClass().getEnumConstants()) {
            if (exceptionEnum.getCode() == code)
                return exceptionEnum;
            }
            return null;
     }
}
