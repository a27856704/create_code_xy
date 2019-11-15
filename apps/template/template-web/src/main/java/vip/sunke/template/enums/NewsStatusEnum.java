package vip.sunke.template.enums;

/**
*    @author sunke
*    @Date 2019-11-15 12:22:07
*    @description 状态
*/

public enum NewsStatusEnum {

    STATUS_1("1", "待审") ,
    STATUS_2("2", "通过") ,
    STATUS__2("-2", "拒绝") ,
    STATUS_3("3", "发布") ; 




    private String type;
    private String desc;

    NewsStatusEnum(String type, String desc) {
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

        for (NewsStatusEnum var : NewsStatusEnum.values()) {
            if (type.equalsIgnoreCase(var.getType()))
                return var.getDesc();
             }
            return NewsStatusEnum.values()[0].getDesc();
        }

}
