package vip.sunke.template.enums;

/**
*    @author sunke
*    @Date 2019-11-15 12:22:07
*    @description 类型
*/

public enum NewsTypeEnum {

    TYPE_1("1", "行业") ,
    TYPE_2("2", "专业") ,
    TYPE_3("3", "科技") ,
    TYPE_4("4", "社会") ; 




    private String type;
    private String desc;

    NewsTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
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

    public static String getDescByType(String type) {
        if(type==null || "".equals(type)){
            type="0";
        }

        for (NewsTypeEnum var : NewsTypeEnum.values()) {
            if (type.equalsIgnoreCase(var.getType()))
                return var.getDesc();
             }
            return NewsTypeEnum.values()[0].getDesc();
        }

}
