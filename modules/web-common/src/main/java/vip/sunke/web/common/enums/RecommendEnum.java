package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2017/4/20 11:04
 * @description
 */

public enum RecommendEnum {


    MEMBER(0, "个人"), COMPANY(1, "企业");
    private int type;
    private String desc;


    RecommendEnum(int type, String desc) {
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

    public static String getDescByType(int type) {

        for (RecommendEnum var : RecommendEnum.values()) {
            if (type == var.getType())
                return var.getDesc();


        }


        return "个人";
    }


}
