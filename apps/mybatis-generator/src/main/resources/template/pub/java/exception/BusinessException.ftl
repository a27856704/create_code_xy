package ${pubPackage}.pubInter.exception;

/**
* @author ${author}
* @Date ${createTime}
* @description 业务异常
*/

public class BusinessException extends SkException {


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ExceptionEnum business) {
        super(business);
    }
}
