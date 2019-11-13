package vip.sunke.mybatis;

import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaDomUtils;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * @author sunke
 * @Date 2018/10/22 10:49
 * @description
 */

public class SkField extends Field {

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    /**
     * @param indentLevel
     * @param compilationUnit
     * @return
     */
    @Override
    public String getFormattedContent(int indentLevel, CompilationUnit compilationUnit) {
        StringBuilder sb = new StringBuilder();


        if (StringUtility.stringHasValue(getRemark())) {

            sb.append("     /**\n" +

                    "     *" + getRemark() + "\n" +

                    "     */\n");
        }


        addFormattedJavadoc(sb, indentLevel);
        addFormattedAnnotations(sb, indentLevel);

        OutputUtilities.javaIndent(sb, indentLevel);
        sb.append(getVisibility().getValue());

        if (isStatic()) {
            sb.append("static "); //$NON-NLS-1$
        }

        if (isFinal()) {
            sb.append("final "); //$NON-NLS-1$
        }

        if (isTransient()) {
            sb.append("transient "); //$NON-NLS-1$
        }

        if (isVolatile()) {
            sb.append("volatile "); //$NON-NLS-1$
        }

        FullyQualifiedJavaType type = getType();
        String name = getName();
        sb.append(JavaDomUtils.calculateTypeName(compilationUnit, type));

        sb.append(' ');
        sb.append(name);
        String initializationString = getInitializationString();


        if (getInitializationString() != null && initializationString.length() > 0) {
            sb.append(" = "); //$NON-NLS-1$
            sb.append(initializationString);
        }

        sb.append(';');

        return sb.toString();
    }
}
