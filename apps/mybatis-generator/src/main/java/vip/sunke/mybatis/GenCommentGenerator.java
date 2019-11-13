package vip.sunke.mybatis;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;
import vip.sunke.mybatis.parser.ColumnRemark;

import java.util.Properties;

/**
 * @author sunke
 * @Date 2017/11/28 15:02
 * @description
 */

public class GenCommentGenerator implements CommentGenerator {


    /**
     * Adds a comment for a model class.  The Java code merger should
     * be notified not to delete the entire class in case any manual
     * changes have been made.  So this method will always use the
     * "do not delete" annotation.
     * <p>
     * Because of difficulties with the Java file merger, the default implementation
     * of this method should NOT add comments.  Comments should only be added if
     * specifically requested by the user (for example, by enabling table remark comments).
     *
     * @param topLevelClass     the top level class
     * @param introspectedTable
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

    }

    /**
     * Adds the inner class comment.
     *
     * @param innerClass        the inner class
     * @param introspectedTable
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

    }

    /**
     * Adds the inner class comment.
     *
     * @param innerClass        the inner class
     * @param introspectedTable the introspected table
     * @param markAsDoNotDelete
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {

    }

    /**
     * Adds properties for this instance from any properties configured in the
     * CommentGenerator configuration.
     * <p>
     * This method will be called before any of the other methods.
     *
     * @param properties All properties from the configuration
     */
    @Override
    public void addConfigurationProperties(Properties properties) {

    }

    /**
     * This method should add a Javadoc comment to the specified field. The
     * field is related to the specified table and is used to hold the value of
     * the specified column.
     * <p>
     * <p>
     * <b>Important:</b> This method should add a the nonstandard JavaDoc tag
     * "@mbggenerated" to the comment. Without this tag, the Eclipse based Java
     * merge feature will fail.
     *
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {


        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("//" + introspectedColumn.getRemarks().split("\\|")[0]);
        }


    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

       /* for(IntrospectedColumn column: introspectedTable.getAllColumns()) {

            if (StringUtility.stringHasValue(column.getRemarks())) {
                field.addJavaDocLine("//" + column.getRemarks().split("\\|")[0]);
            }

        }*/

    }


    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" * 获取");
            sb.append(introspectedColumn.getRemarks().split("\\|")[0]);
            method.addJavaDocLine(sb.toString());
            method.addJavaDocLine(" *");
        }
        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" - ");
            sb.append(introspectedColumn.getRemarks().split("\\|")[0]);
        }
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");


    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        ColumnRemark columnRemark = new ColumnRemark();
        columnRemark.parse(introspectedColumn.getRemarks());

        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {


            sb.append(" * 设置");
            sb.append(columnRemark.getDescName());
            method.addJavaDocLine(sb.toString());
            if(columnRemark.getValueString()!=null)
                method.addJavaDocLine(" * "+columnRemark.getValueString().replace("@", ":"));

            method.addJavaDocLine(" *");
        }
        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        if (StringUtility.stringHasValue(columnRemark.getDescName())) {
            sb.append(" ");
            sb.append(columnRemark.getDescName());
        }
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");





    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }

    /**
     * This method is called to add a file level comment to a generated java
     * file. This method could be used to add a general file comment (such as a
     * copyright notice). However, note that the Java file merge function in
     * Eclipse does not deal with this comment. If you run the generator
     * repeatedly, you will only retain the comment from the initial run.
     * <p>
     * <p>
     * The default implementation does nothing.
     *
     * @param compilationUnit
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }

    /**
     * This method should add a suitable comment as a child element of the
     * specified xmlElement to warn users that the element was generated and is
     * subject to regeneration.
     *
     * @param xmlElement
     */
    @Override
    public void addComment(XmlElement xmlElement) {

    }

    /**
     * This method is called to add a comment as the first child of the root
     * element. This method could be used to add a general file comment (such as
     * a copyright notice). However, note that the XML file merge function does
     * not deal with this comment. If you run the generator repeatedly, you will
     * only retain the comment from the initial run.
     * <p>
     * <p>
     * The default implementation does nothing.
     *
     * @param rootElement
     */
    @Override
    public void addRootComment(XmlElement rootElement) {

    }
}
