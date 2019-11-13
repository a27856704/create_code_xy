package vip.sunke.mybatis.parser;

/**
 * @author sunke
 * @Date 2019-04-28 10:09
 * @description
 */

public class ColumnValue {

    private String value;
    private String desc;

    public ColumnValue(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public ColumnValue() {
    }

    public ColumnValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
