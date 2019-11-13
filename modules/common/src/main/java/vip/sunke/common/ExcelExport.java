package vip.sunke.common;

import org.apache.commons.collections.map.ListOrderedMap;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @Description: 导出excel功能
 */
public class ExcelExport {

    /**
     * 导出excel文件,在控制器里调用还需要加上下面的header
     * response.setContentType("application/vnd.ms-excel;charset=gb2312");
     * response
     * .setHeader("Content-disposition","attachment; filename=shuju.xls");
     *
     * @param title 标题
     * @param data  数据
     */
    @SuppressWarnings("unchecked")
    public static String exportToExcel(String[] title, List<ListOrderedMap> data) {
        StringBuilder sb = new StringBuilder(200);
        sb.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><style type=\"text/css\">body{font-family:\"SIMHEI\";} tr{height:28px;} #blue{background-color:#33CCCC;} td{text-align:center}</sytle></head>");
        sb.append("<body>");
        sb.append("<table border=1><tr>");
        for (String s : title) {
            sb.append("<th>").append(s).append("</th>");
        }
        sb.append("</tr>");
        for (int i = 0; i < data.size(); i++) {
            sb.append("<tr>");
            Set<Entry<String, ?>> entrySet2 = data.get(i).entrySet();
            for (Entry<String, ?> entry : entrySet2) {
                if (entry.getValue() == null) {
                    sb.append("<td style='vnd.ms-excel.numberformat:@'>")
                            .append("").append("</td>");
                } else {
                    sb.append("<td style='vnd.ms-excel.numberformat:@'>")
                            .append(entry.getValue()).append("</td>");
                }
            }
            sb.append("</tr>");
        }
        sb.append("</table></body></html>");
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static String exportToExcel2(String[] title, List<ListOrderedMap> data, String headline) {
        StringBuilder sb = new StringBuilder(200);
        sb.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><style type=\"text/css\">body{font-family:\"SIMHEI\";} tr{height:28px;} #blue{background-color:#33CCCC;} td{text-align:center}</sytle></head>");
        sb.append("<body>");
        sb.append("<table border=1><tr><td colspan=" + title.length + " style='font-weight:bold ;text-align:center;vnd.ms-excel.numberformat:@'>" + headline + "</td></tr><tr>");
        for (String s : title) {
            sb.append("<th>").append(s).append("</th>");
        }
        sb.append("</tr>");
        for (int i = 0; i < data.size(); i++) {
            sb.append("<tr>");
            Set<Entry<String, ?>> entrySet2 = data.get(i).entrySet();
            for (Entry<String, ?> entry : entrySet2) {
                if (entry.getValue() == null) {
                    sb.append("<td style='vnd.ms-excel.numberformat:@'>")
                            .append("").append("</td>");
                } else {
                    sb.append("<td style='vnd.ms-excel.numberformat:@'>")
                            .append(entry.getValue()).append("</td>");
                }
            }
            sb.append("</tr>");
        }
        sb.append("</table></body></html>");
        return sb.toString();
    }


}
