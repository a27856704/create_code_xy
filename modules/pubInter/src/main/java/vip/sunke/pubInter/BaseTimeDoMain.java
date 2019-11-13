package vip.sunke.pubInter;

import java.util.Date;

/**
 * @author sunke
 * @Date 2017/10/24 14:56
 * @description
 */

public class BaseTimeDoMain extends BaseIdDoMain<String> {


    private Date createTime;
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
