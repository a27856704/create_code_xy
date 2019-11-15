package vip.sunke.template.enums;

/**
*    @author sunke
*    @Date 2019-11-15 12:22:07
*    @description 
*/

public enum NewsFlagEnum {

    CREATE("0", "新增") ,
    UP("1", "上架") ,
    DOWN("2", "下架") ; 




    private String type;
    private String desc;

    NewsFlagEnum(String type, String desc) {
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

        for (NewsFlagEnum var : NewsFlagEnum.values()) {
            if (type.equalsIgnoreCase(var.getType()))
                return var.getDesc();
             }
            return NewsFlagEnum.values()[0].getDesc();
        }

}
