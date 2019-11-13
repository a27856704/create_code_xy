package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2018/12/12 14:45
 * @description
 */

public enum CompanyRoleFlagEnum {


    PERSON(1, "个人"), AGENT(8, "经办人"), LEGAL(2, "法人"), SEAL(4, "法人授权公章持有者"),;
    private int status;
    private String desc;


    CompanyRoleFlagEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDescByStatus(int status) {

        for (CompanyRoleFlagEnum var : CompanyRoleFlagEnum.values()) {
            if (status == var.getStatus())
                return var.getDesc();
        }
        return "经办人";
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
