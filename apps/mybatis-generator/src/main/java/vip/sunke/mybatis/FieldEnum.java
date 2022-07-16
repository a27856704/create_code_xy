package vip.sunke.mybatis;

import lombok.Data;

/**
 * @author sunke
 * @Date 2019/11/15 10:35
 * @description
 */

@Data
public class FieldEnum {

    private String name;//字段名称
    private String type;//字段值
    private String desc;//字段描述
    private boolean isNumber;//是否数字
}
