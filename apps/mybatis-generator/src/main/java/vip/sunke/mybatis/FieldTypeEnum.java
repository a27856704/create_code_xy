package vip.sunke.mybatis;

/**
 * @author sunke
 * @Date 2019-05-06 15:44
 * @description
 */

public enum FieldTypeEnum {


    VARCHAR("varchar", "varchar", false),
    INT("int", "int", false),
    FLOAT("float", "float", true),
    DOUBLE("double", "double", true),
    DECIMAL("decimal", "decimal", true),
    DATETIME("datetime", "datetime", false),
    TEXT("text", "text", false),
    LONGTEXT("longtext", "longtext", false),


    ;
    private String type;
    private String desc;
    private boolean decimals;

    FieldTypeEnum(String type, String desc, boolean decimals) {
        this.type = type;
        this.desc = desc;
        this.decimals = decimals;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isDecimals() {
        return decimals;
    }

    public void setDecimals(boolean decimals) {
        this.decimals = decimals;
    }

    public static FieldTypeEnum getEnumByType(String type) {

        for (FieldTypeEnum fieldTypeEnum : FieldTypeEnum.values()) {

            if (fieldTypeEnum.type.equalsIgnoreCase(type))
                return fieldTypeEnum;

        }

        return TEXT;
    }

}
