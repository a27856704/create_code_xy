package vip.sunke.template.enums;

/**
*    @author sunke
*    @Date 2019-11-15 12:22:07
*    @description 是否显示
*/

public enum NewsShowEnum {

    SHOW_1("1", "显示") ,
    SHOW_0("0", "隐藏") ; 




    private String type;
    private String desc;

    NewsShowEnum(String type, String desc) {
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

        for (NewsShowEnum var : NewsShowEnum.values()) {
            if (type.equalsIgnoreCase(var.getType()))
                return var.getDesc();
             }
            return NewsShowEnum.values()[0].getDesc();
        }

}
