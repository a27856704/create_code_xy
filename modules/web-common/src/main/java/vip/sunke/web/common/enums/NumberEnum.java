/**
 *
 */
package vip.sunke.web.common.enums;

/**
 * @author Administrator
 */
public enum NumberEnum {

    NEGATIVE(-1, "-1"), ZERO(0, "0"), ONE(1, "1"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5");

    private int value;
    private String desc;


    NumberEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
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
