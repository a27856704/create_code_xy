package vip.sunke.mybatis.parser;

/**
 * @author sunke
 * @Date 2019-04-28 09:49
 * @description
 */

@Deprecated
public interface IParser {

    /**
     * 添加页面
     *
     * @param remark
     * @return
     */
    String parse(ColumnRemark remark);

    /**
     * 搜索条件输入
     *
     * @param remark
     * @return
     */
    String parseSearch(ColumnRemark remark);

}
