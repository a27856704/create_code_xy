package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2018/12/12 14:45
 * @description
 */

public enum ContractSignTypeEnum {


    PERSON(0, "个人"), AGENT(1, "经办人"), LEGAL(2, "法人"), SEAL(4, "公章"),;
    private int status;
    private String desc;


    ContractSignTypeEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDescByStatus(int status) {

        for (ContractSignTypeEnum var : ContractSignTypeEnum.values()) {
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
