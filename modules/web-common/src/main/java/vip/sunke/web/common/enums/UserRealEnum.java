package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2017/4/26 12:12
 * @description
 */

public enum UserRealEnum {

    TRUENAME(1, "实名"), EMAIL(2, "邮箱");

    private int type;
    private String desc;


    UserRealEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(int type) {

        for (UserRealEnum var : UserRealEnum.values()) {
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
