package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2018/12/12 14:45
 * @description
 */

public enum ContractReceiverTypeEnum {


    PERSON(1, "个人"), COMPANY(2, "企业");
    private int status;
    private String desc;


    ContractReceiverTypeEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDescByStatus(int status) {

        for (ContractReceiverTypeEnum var : ContractReceiverTypeEnum.values()) {
            if (status == var.getStatus())
                return var.getDesc();
        }
        return "个人";
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
