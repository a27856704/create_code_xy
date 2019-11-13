package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2018/12/12 14:45
 * @description
 */

public enum SignStatusEnum {


    WAIT(0, "待签"), SIGN(1, "已签"), REFUSE(2, "拒签"),;
    private int status;
    private String desc;


    SignStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDescByStatus(int status) {

        for (SignStatusEnum var : SignStatusEnum.values()) {
            if (status == var.getStatus())
                return var.getDesc();
        }
        return "待签";
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
