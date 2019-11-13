package vip.sunke.template.enums;

import vip.sunke.pubInter.exception.ExceptionEnum;

/**
 * @author zhang
 * @Date 2018/8/15 21:34
 * @description
 */
public enum UserExceptionEnum implements ExceptionEnum<UserExceptionEnum> {

    USER_FRONT_LOGIN_ERROR(2000, "user.front.login.error", "手机号码或密码错误"),
    USER_FRONT_NOT_EXIST(2005, "user.front.not.exist", "用户不存在"),

    USER_FRONT_EXIST(2010, "user.front.exist", "手机号码已存在"),
    USER_NOT_LOGIN(2015, "user.not.login", "用户未登录"),

    USER_CODE_ERROR(2020, "user.reg.code.error", "验证码有误"),

    USER_FARBIDDEN_ERROR(2025, "user.farbidden.error", "账号已禁用"),

    PASSWORD_ERROR(2030, "password.error", "用户密码格式不正确"),
    PASSWORD_CONFIRM_ERROR(2035, "password.confirm.error", "两次密码不同"),
    PHONE_NULL(2040, "phone.null", "手机号码为空"),

    NO_RIGHTS(2045, "no.rights", "无权限"),

    PASSWORD_NULL(2050, "password.null", "密码为空"),

    PASSWORD_NOT_SAME(2055, "password.not.same", "两次密码不一致"),
    IDCARD_ERROR(2060, "idcard.error", "身份证格式不正确"),
    LOGIN_MOBILE_ERROR(2065, "login.mobile.error", "手机格式不正确"),
    MOBILE_NOT_RIGHT(2070, "mobile.not.right", "手机号与信息中不一致"),
    USERNAME_MOBILE_NULL(2075, "username.mobile.null", "用户名或登录手机为空"),

    VERIFYCODE_ERROR(2080, "verifyCode.error", "验证码为空"),
    USERTYPE_ERROR(2085, "user.type.error", "用户类型为空"),

    IDCARD_EXIST_ERROR(2090, "idcard.exist.error", "身份证已被使用"),
    SMS_TOKEN_NULL(2095, "sms.token.null", "短信验证码过期"),
    PASSWORD_OLD_ERROR(2100, "password.old.error", "原密码有误"),;

    private int code;
    private String key;
    private String message;

    UserExceptionEnum(int code, String key, String message) {
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

    @Override
    public UserExceptionEnum getEnum(int code) {
        return null;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
