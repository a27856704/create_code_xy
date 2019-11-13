package vip.sunke.mybatis;

/**
 * @author sunke
 * @Date 2019-04-28 09:23
 * @description
 */

public enum YesOrNoEnum {


    YES(1, "是"),
    NO(0, "否");


    private int type;
    private String desc;

    YesOrNoEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
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
