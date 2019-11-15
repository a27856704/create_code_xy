package vip.sunke.mybatis.parser;

/**
 * @author sunke
 * @Date 2019-04-28 10:09
 * @description
 */

public class ColumnValue {

    private String value;
    private String desc;
    private String enName;

    public ColumnValue(String value, String desc,String enName) {
        this.value = value;
        this.enName=enName;
        this.desc = desc;
    }

    public ColumnValue() {
    }

    public ColumnValue(String value) {
        this.value = value;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;

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
