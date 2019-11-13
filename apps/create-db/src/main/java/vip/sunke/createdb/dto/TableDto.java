package vip.sunke.createdb.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunke
 * @Date 2019-05-05 13:43
 * @description
 */
@Data
public class TableDto implements Serializable {

    private String tableName;

    private String fieldPrefix;//字段前缀

    private List<FieldDto> fieldList;


}
