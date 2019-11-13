package vip.sunke.web.common.enums;

/**
 * @author sunke
 * @Date 2018/12/12 14:45
 * @description
 */

public enum SignOrderEnum {


    ORDER(0, "顺序"), UNORDERED(1, "无序");
    private int status;
    private String desc;


    SignOrderEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDescByStatus(int status) {

        for (SignOrderEnum var : SignOrderEnum.values()) {
            if (status == var.getStatus())
                return var.getDesc();
        }
        return "顺序";
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
