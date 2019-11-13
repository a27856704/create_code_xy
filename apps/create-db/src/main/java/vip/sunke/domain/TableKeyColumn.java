package vip.sunke.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunke
 * @Date 2019-06-25 16:41
 * @description
 */

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TableKeyColumn {

    private String tableName;//表名
    private String keyColumn;//主键
    private String prefix = "";//前缀
    private String objectName;//类名




}
