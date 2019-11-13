package vip.sunke.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author sunke
 * @Date 2019-06-24 14:20
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableComment {
    private String name;
    private String comment;

    private List<TableField> fieldList;
}
