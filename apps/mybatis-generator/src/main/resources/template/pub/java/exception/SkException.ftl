package ${pubPackage}.pubInter.exception;

/**
* @author ${author}
* @Date ${createTime}
* @description 异常基类
*/

public abstract class SkException extends Exception {

    private static final long serialVersionUID = 1L;

    public static String SPLIT_MESSAGE = "@@";
    public static String DEFALUT_CODE = "0000";

    private String msgError;
    private String codeError;


    public SkException(String message) {
        super(message);
        this.msgError = message;
        this.codeError = DEFALUT_CODE;
    }


    public SkException(ExceptionEnum business) {

        super(((business != null) ? business.getMessage() : "") + SPLIT_MESSAGE + ((business != null) ? business.getCode() : DEFALUT_CODE));
        this.msgError = (business != null) ? business.getMessage() : "";
        this.codeError = (business != null) ? business.getCode() + "" : DEFALUT_CODE;

    }


    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public String getCodeError() {
        return codeError;
    }

    public void setCodeError(String codeError) {
        this.codeError = codeError;
    }

}
