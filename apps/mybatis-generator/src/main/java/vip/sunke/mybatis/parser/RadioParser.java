package vip.sunke.mybatis.parser;

import java.util.List;

/**
 * @author sunke
 * @Date 2019-04-28 09:51
 * @description input type="text"
 */
@Deprecated
public class RadioParser implements IParser {


    /**
     * 搜索条件输入
     *
     * @param remark
     * @return
     */
    @Override
    public String parseSearch(ColumnRemark remark) {
        return null;
    }

    @Override
    public String parse(ColumnRemark remark) {


        StringBuffer buffer = new StringBuffer();
        List<ColumnValue> valueList = remark.getValueList();

        if (valueList != null && valueList.size() > 0) {


            for (ColumnValue value : valueList) {
                buffer.append(" <input type=\"radio\" class=\"radio-box\"");
                buffer.append(" value=\"" + value.getValue() + "\"");
                buffer.append(" " + ((value.getValue().equalsIgnoreCase(remark.getDefaultValue())) ? "checked" : ""));
                buffer.append(" name=\"" + remark.getName() + "\"");
                buffer.append(" title=\"" + value.getDesc() + "\"");
                buffer.append("/>");
                buffer.append(value.getDesc());
            }

        }


        return buffer.toString();
    }


}
