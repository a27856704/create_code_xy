package vip.sunke.createdb.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sunke
 * @Date 2019-05-05 13:55
 * @description
 */
@Data
public class FieldValueDto implements Serializable {
    private String value;
    private String desc;
}
