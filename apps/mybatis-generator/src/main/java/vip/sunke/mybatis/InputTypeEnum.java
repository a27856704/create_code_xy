package vip.sunke.mybatis;

/**
 * @author sunke
 * @Date 2019-04-28 09:14
 * @description 输入类型
 */

public enum InputTypeEnum {


    TEXT(1, "TEXT", "TEXT"),
    RADIO(2, "RADIO", "RADIO"),

    CHECKBOX(3, "CHECKBOX", "CHECKBOX"),

    SELECT(4, "SELECT", "SELECT"),

    TEXTAREA(5, "TEXTAREA", "TEXTAREA"),
    EDIT(6, "EDIT", "HTML编辑器"),
    DATE(7, "DATE", "时间选择器"),
    FILE(8, "FILE", "上传文件"),
    FILE_MULTIPLE(9, "FILE", "多文件");

    private int type;
    private String tag;
    private String desc;


    InputTypeEnum(int type, String tag, String desc) {
        this.type = type;
        this.desc = desc;
        this.tag = tag;
    }

    public static String getDescByType(int type) {

        for (InputTypeEnum inputTypeEnum : InputTypeEnum.values()) {

            if (inputTypeEnum.type == type)
                return inputTypeEnum.getDesc();

        }

        return TEXT.desc;
    }

    public static InputTypeEnum getEnumByType(int type) {

        for (InputTypeEnum inputTypeEnum : InputTypeEnum.values()) {

            if (inputTypeEnum.type == type)
                return inputTypeEnum;

        }

        return TEXT;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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