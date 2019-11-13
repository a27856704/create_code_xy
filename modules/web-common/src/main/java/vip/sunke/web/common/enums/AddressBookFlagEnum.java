package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2018/12/10 22:20
 * @description
 */

public enum AddressBookFlagEnum {

    PERSON(0, "个人"), COMPANY(1, "企业");

    private int value;
    private String desc;

    AddressBookFlagEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDescByValue(int value) {

        for (AddressBookFlagEnum var : AddressBookFlagEnum.values()) {
            if (value == var.getValue())
                return var.getDesc();
        }
        return "个人";
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
