package vip.sunke.template.enums;

/**
*    @author sunke
*    @Date 2019-11-15 12:22:07
*    @description 作者
*/

public enum NewsAuthorEnum {

    AUTHOR_1("1", "张三") ,
    AUTHOR_2("2", "李四") ,
    AUTHOR_3("3", "王五") ,
    AUTHOR_4("4", "赵六") ; 




    private String type;
    private String desc;

    NewsAuthorEnum(String type, String desc) {
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

        for (NewsAuthorEnum var : NewsAuthorEnum.values()) {
            if (type.equalsIgnoreCase(var.getType()))
                return var.getDesc();
             }
            return NewsAuthorEnum.values()[0].getDesc();
        }

}
