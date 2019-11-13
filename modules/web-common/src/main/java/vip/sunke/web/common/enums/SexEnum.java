package vip.sunke.web.common.enums;

/**
 * @author zhangxuchen
 * @date 2018/8/15
 * @description 性别
 */
public enum SexEnum {
    MAN(0, "男"), WOMAN(1, "女");

    private int value;
    private String desc;

    SexEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getSex(int value) {

        for (SexEnum var : SexEnum.values()) {
            if (value == var.value)
                return var.getDesc();
        }
        return MAN.getDesc();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
