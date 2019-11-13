package ${pubPackage}.pubInter.exception;


/**
* @author ${author}
* @Date ${createTime}
* @description 业务异常枚举
*/

public enum BusinessExceptionEnum implements ExceptionEnum<BusinessExceptionEnum> {

    SYSTEM_ERROR(2000, "system.error", "系统异常"),
    NOT_FIND_PAGE(2005, "not.find.page", "该网页无法打开"),
    OBJ_NULL_ERROR(2010, "obj.null.error", "对象空"),
    EMPTY_ERROR(2015, "empty.error", "空字符"),
    RIGHTS_NO_ERROR(2020, "rights.no.error", "无权限操作"),
    TOKEN_NULL(2025, "token.null", "token不存在或过期"),
    FILE_FORMAT_ERROR(2030, "file.format.error", "文件格式有误"),
    FILE_NULL_ERROR(2035, "file.null.error", "文件为空");


    private int code;
    private String key;
    private String message;

    BusinessExceptionEnum(int code, String key, String message) {
        this.code = code;
        this.key = key;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public BusinessExceptionEnum getEnum(int code) {
        for (BusinessExceptionEnum business : BusinessExceptionEnum.values()) {
            if (code == business.getCode())
                return business;
        }
        return null;
    }
}
