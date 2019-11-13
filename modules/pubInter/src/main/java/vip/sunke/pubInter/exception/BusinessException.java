package vip.sunke.pubInter.exception;

/**
 * @author sunke
 * @Date 2019-09-04 17:36
 * @description
 */

public class BusinessException extends SkException {


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ExceptionEnum business) {
        super(business);
    }
}
