package vip.sunke.createdb.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunke
 * @Date 2019-05-05 13:42
 * @description
 */

@Data
public class FieldDto implements Serializable {



    private String db;
    private String tableName;



    private String fieldPrefix;//字段前缀

    private String name;//字段名称
    //java.sql.Types
    private String type;
    private int length;
    private int decimals;
    private boolean nullable;//是否可以为null
    private boolean identity;//是否是主键
    private String defaultValue;//默认值

    private String descName;//名称
    private int searchFlag;//搜索
    private boolean showPage;//添加或修改显示

    private boolean showListPage;//列表时显示
    private boolean showDetailPage;//详情时显示

    private boolean need;//必选
    private int inputType;//输入框

    private List<FieldValueDto> fieldValueList;




}
