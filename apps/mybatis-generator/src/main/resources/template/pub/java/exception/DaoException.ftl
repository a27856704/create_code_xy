package ${pubPackage}.pubInter.exception;

/**
* @author ${author}
* @Date ${createTime}
* @description DAO异常
*/

public class DaoException extends SkException {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(ExceptionEnum business) {
        super(business);
    }
}
