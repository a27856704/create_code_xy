package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2018/12/12 14:45
 * @description
 */

public enum ContractStatusEnum {


    NEW(0, "新建"), SIGN(1, "签署中"), FINISH(2, "完成"), CANCEL(3, "撤回"), INVALID(4, "作废"),;
    private int status;
    private String desc;


    ContractStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
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

    public static String getDescByStatus(int status) {

        for (ContractStatusEnum var : ContractStatusEnum.values()) {
            if (status == var.getStatus())
                return var.getDesc();
        }
        return "新建";
    }
}
