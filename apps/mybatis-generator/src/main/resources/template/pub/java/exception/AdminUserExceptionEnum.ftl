package ${package};


import ${commonPackage}.enums.ExceptionEnum;
import ${webCommonPackage}.constant.CodeConstant;

/**
*    @author ${author}
*    @Date ${date}
*    @description
*/
public enum AdminUserExceptionEnum implements ExceptionEnum<AdminUserExceptionEnum> {


    ADMIN_NAME_EMPTY(CodeConstant.ADMIN, "admin.name.empty", "用户名为空")
    , ADMIN_PWD_EMPTY(CodeConstant.ADMIN + 10, "admin.pwd.empty", "密码为空"), ADMIN_PWD_ERROR(CodeConstant.ADMIN + 20, "admin.pwd.error", "密码错误"), ADMIN_NAME_NO_EXIST(CodeConstant.ADMIN + 30, "admin.username.no.exist", "用户不存在"), ADMIN_NOT_LOGIN(CodeConstant.ADMIN + 40, "admin.not.login", "后台用户未登录"), ADMIN_DISABLE(CodeConstant.ADMIN + 50, "admin.not.disable", "禁用"), ADMIN_NAME_EXIST(CodeConstant.ADMIN + 60, "admin.username.exist", "用户已存在");


    private int code;
    private String key;
    private String message;

    AdminUserExceptionEnum(int code, String key, String message) {
        this.code = code;
        this.key = key;
        this.message = message;
    }


    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
