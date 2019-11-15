package vip.sunke.template.enums;

/**
*    @author sunke
*    @Date 2019-11-15 12:22:07
*    @description 来源
*/

public enum NewsSourceEnum {

    SOURCE_1("1", "新浪") ,
    SOURCE_2("2", "腾讯") ,
    SOURCE_3("3", "知乎") ; 




    private String type;
    private String desc;

    NewsSourceEnum(String type, String desc) {
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

        for (NewsSourceEnum var : NewsSourceEnum.values()) {
            if (type.equalsIgnoreCase(var.getType()))
                return var.getDesc();
             }
            return NewsSourceEnum.values()[0].getDesc();
        }

}
