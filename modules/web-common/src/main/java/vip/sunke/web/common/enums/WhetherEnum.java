package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2018/12/10 22:20
 * @description
 */

public enum WhetherEnum {

    NO(0, "否"), YES(1, "是");

    private int value;
    private String desc;

    WhetherEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDescByValue(int value) {

        for (WhetherEnum var : WhetherEnum.values()) {
            if (value == var.getValue())
                return var.getDesc();
        }
        return "否";
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
