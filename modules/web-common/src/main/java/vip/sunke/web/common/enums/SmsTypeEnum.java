package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2017/4/26 12:12
 * @description
 */

public enum SmsTypeEnum {

    REG(1, "注册"), PSW_FORGET(2, "忘记密码"),TRUENAME(3,"实名"),PSW(4,"修改密码");

    private int type;
    private String desc;


    SmsTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(int type) {

        for (SmsTypeEnum var : SmsTypeEnum.values()) {
            if (var.getType() == type) {
                return var.getDesc();
            }

        }

        return "注册";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
