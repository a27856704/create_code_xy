package vip.sunke.pubInter.exception;

/**
 * @author sunke
 * @Date 2019-09-04 17:33
 * @description
 */

public class DaoException extends SkException {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(ExceptionEnum business) {
        super(business);
    }
}
