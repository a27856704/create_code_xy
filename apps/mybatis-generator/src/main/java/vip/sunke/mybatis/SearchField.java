package vip.sunke.mybatis;

import lombok.Data;

/**
 * @author sunke
 * @Date 2019/12/10 10:38
 * @description
 */
@Data
public class SearchField {

    private String javaType;
    private String filedName;
    private String entityName;
    private String searchClassName;
    private int searchFlag;
    private String remark;
    private String getMethodName;
    private String setMethodName;



}
