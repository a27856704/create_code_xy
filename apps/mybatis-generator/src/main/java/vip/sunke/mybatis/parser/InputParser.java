package vip.sunke.mybatis.parser;

/**
 * @author sunke
 * @Date 2019-04-28 09:51
 * @description input type="text"
 */
@Deprecated
public class InputParser implements IParser {

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

        buffer.append(" <input type=\"text\" class=\"input-text\"");


        buffer.append(" value=\"" + remark.getDefaultValue() != null ? "" : remark.getDefaultValue() + "\"");

        buffer.append(" placeholder=\"" + remark.getDescName() + "\"");
        buffer.append(" id=\"" + remark.getName() + "\"");
        buffer.append(" name=\"" + remark.getName() + "\"");
        buffer.append("/>");


        return buffer.toString();
    }


}
