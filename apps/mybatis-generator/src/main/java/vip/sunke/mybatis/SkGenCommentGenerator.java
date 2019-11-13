package vip.sunke.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.util.StringUtility;
import vip.sunke.mybatis.parser.ColumnRemark;

/**
 * @author sunke
 * @Date 2019/1/30 14:34
 * @description
 */

public class SkGenCommentGenerator extends GenCommentGenerator {


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {


        StringBuilder sb = new StringBuilder();
        String remark = "";

        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {

            ColumnRemark columnRemark = new ColumnRemark();
            columnRemark.parse(introspectedColumn.getRemarks());
            field.addJavaDocLine("/**");


            remark = columnRemark.getDescName();
            sb.append(" * "+remark);
            field.addJavaDocLine(sb.toString());
            if(columnRemark.getValueString()!=null)
                field.addJavaDocLine(" * "+columnRemark.getValueString().replace("@", ":"));
            field.addJavaDocLine(" */");

        }

        if ("".equals(remark)) {
            remark = introspectedColumn.getJavaProperty();
        }

        int len = introspectedColumn.getLength();
        String javaType = introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName();

        String javaTpeString = "java.lang.String";
        String jdbcTypeName = introspectedColumn.getJdbcTypeName();

        if (!introspectedColumn.isNullable() && !introspectedColumn.isIdentity()) {
            if (javaTpeString.equals(javaType)) {
                field.addAnnotation("@NotBlank(message = \"" + remark + "不能为空\")");
            } else {
                field.addAnnotation("@NotNull(message = \"" + remark + "不能为空\")");
            }
        }

        if (!"LONGVARCHAR".equalsIgnoreCase(jdbcTypeName)
                && !"CLOB".equalsIgnoreCase(jdbcTypeName)
                && !"BLOB".equalsIgnoreCase(jdbcTypeName)


        ) {

            if (javaTpeString.equals(javaType) && !introspectedColumn.isIdentity()) {
                field.addAnnotation("@Length(max=" + len + ",message=\"" + remark + "长度最大值" + len + "\")");
            }


        }


    }
}
