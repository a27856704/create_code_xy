package vip.sunke.mybatis;

import vip.sunke.mybatis.parser.ColumnRemark;

/**
 * @author sunke
 * @Date 2019-04-28 15:20
 * @description 创建搜索条件类字段
 */

public class SearchFieldBuild {


    private static BeanName beanName = new BeanName();


    public static String buildBitSearchName(boolean field, String entityName, ColumnRemark columnRemark) {
        String javaName=columnRemark.getName();
        StringBuffer sf = new StringBuffer();
        String filedName = beanName.getFirstLowerCase(javaName) + SearchTypeEnum.BIT.getSuffixStart();
        String searchClassName = beanName.getShortSearchClassName(entityName);

        if (field) {
            sf.append("    @ApiModelProperty(value=\""+columnRemark.getDescName()+"\")\n");
            sf.append("    private int " + filedName + "=0;\n");
        } else {
            sf.append("    public  " + searchClassName + " " + beanName.setMethodName(filedName) + "(int " + filedName + "){\n");
            sf.append("        this." + filedName + "=" + filedName + ";\n");
            sf.append("        if(" + filedName + ">0){\n");
            sf.append("            setBitField(" + entityName + "." + beanName.underscoreName(javaName).toUpperCase() + ", " + filedName + ");\n");

            sf.append("        }\n");
            sf.append("        return this;\n");
            sf.append("    }\n");


            sf.append("    public int " + beanName.getMethodName(filedName) + "(){\n");

            sf.append("        return this." + filedName + ";\n");
            sf.append("    }\n\n");
        }

        return sf.toString();
    }


    /**
     * @param entityName

     * @param start      是否in
     * @return
     */
    public static String buildDateSearchName(boolean field, String entityName, ColumnRemark columnRemark, boolean start) {
        String javaName=columnRemark.getName();


        StringBuffer sf = new StringBuffer();
        String searchClassName = beanName.getShortSearchClassName(entityName);


        String filedName = beanName.getFirstLowerCase(javaName) + SearchTypeEnum.getSuffixByType(SearchTypeEnum.DATE.getType(), start);
        if (field) {
            sf.append("    @ApiModelProperty(value=\""+columnRemark.getDescName()+"\")\n");
            sf.append("    private java.util.Date " + filedName + " =null;\n");
        } else {

            sf.append("    public  " + searchClassName + " " + beanName.setMethodName(filedName) + "(java.util.Date " + filedName + "){\n");


            sf.append("        this." + filedName + "=" + filedName + ";\n");


            if (start) {

                sf.append("        setDateStartField(" + entityName + "." + beanName.underscoreName(javaName).toUpperCase() + ", " + filedName + ");\n");
            } else {
                sf.append("        setDateEndField(" + entityName + "." + beanName.underscoreName(javaName).toUpperCase() + ", " + filedName + ");\n");

            }
            sf.append("        return this;\n");
            sf.append("    }\n");


            sf.append("    public java.util.Date " + beanName.getMethodName(filedName) + "(){\n");

            sf.append("        return this." + filedName + ";\n");
            sf.append("    }\n\n");
        }

        return sf.toString();
    }

    /**
     * @param entityName

     * @param in         是否in
     * @return
     */
    public static String buildInSearchName(boolean field, String entityName, ColumnRemark columnRemark, boolean in) {

        String javaName=columnRemark.getName();

        StringBuffer sf = new StringBuffer();

        String filedName = beanName.getFirstLowerCase(javaName) + (in ? SearchTypeEnum.IN.getSuffixStart() : SearchTypeEnum.NOT_IN.getSuffixStart());
        String searchClassName = beanName.getShortSearchClassName(entityName);
        if (field) {
            sf.append("    @ApiModelProperty(value=\""+columnRemark.getDescName()+"\")\n");
            sf.append("    private java.util.List<Object> " + filedName + " =null;\n");
        } else {

            sf.append("    public  " + searchClassName + " " + beanName.setMethodName(filedName) + "(java.util.List<Object> " + filedName + "){\n");

            sf.append("        this." + filedName + "=" + filedName + ";\n");


            if (in) {
                sf.append("        setInField(" + entityName + "." + beanName.underscoreName(javaName).toUpperCase() + ", " + filedName + ");\n");
            } else {
                sf.append("        setNotInField(" + entityName + "." + beanName.underscoreName(javaName).toUpperCase() + ", " + filedName + ");\n");

            }

            sf.append("        return this;\n");
            sf.append("    }\n");


            sf.append("    public java.util.List<Object> " + beanName.getMethodName(filedName) + "(){\n");

            sf.append("        return this." + filedName + ";\n");
            sf.append("    }\n\n");
        }

        return sf.toString();
    }


    /**
     * @param entityName

     * @param greater    是否大于
     * @return
     */
    public static String buildNumberSearchName(boolean field, String entityName,ColumnRemark columnRemark, boolean greater) {

        String javaName=columnRemark.getName();
        StringBuffer sf = new StringBuffer();
        String filedName = beanName.getFirstLowerCase(javaName) + SearchTypeEnum.getSuffixByType(SearchTypeEnum.NUMBER.getType(), greater);
        String searchClassName = beanName.getShortSearchClassName(entityName);
        if (field) {
            sf.append("    @ApiModelProperty(value=\""+columnRemark.getDescName()+"\")\n");
            sf.append("    private Integer " + filedName + "= null;\n");
        } else {

            sf.append("    public  " + searchClassName + " " + beanName.setMethodName(filedName) + "(Integer " + filedName + "){\n");


            sf.append("        this." + filedName + "=" + filedName + ";\n");

            sf.append("        if(" + filedName + "!=null){\n");


            if (greater) {


                sf.append("        setGreaterField(" + entityName + "." + beanName.underscoreName(javaName).toUpperCase() + ", " + filedName + ");\n");
            } else {
                sf.append("        setLessField(" + entityName + "." + beanName.underscoreName(javaName).toUpperCase() + ", " + filedName + ");\n");

            }

            sf.append("        }\n");

            sf.append("        return this;\n");
            sf.append("    }\n");


            sf.append("    public Integer " + beanName.getMethodName(filedName) + "(){\n");

            sf.append("        return this." + filedName + ";\n");
            sf.append("    }\n\n");
        }

        return sf.toString();


    }


    /**
     * @param entityName 类名

     */
    public static String buildStringSearchName(boolean field, String entityName, ColumnRemark columnRemark,  boolean eq) {

        String javaName=columnRemark.getName();
        StringBuffer sf = new StringBuffer();
        String filedName = beanName.getFirstLowerCase(javaName) + (eq ? SearchTypeEnum.EQUAL.getSuffixStart() : SearchTypeEnum.LIKE.getSuffixStart());

        String searchClassName = beanName.getShortSearchClassName(entityName);

        if (field) {
            sf.append("    @ApiModelProperty(value=\""+columnRemark.getDescName()+"\")\n");
            sf.append("    private String " + filedName + ";\n");
        } else {


            sf.append("    public " + searchClassName + " " + beanName.setMethodName(filedName) + "(String " + filedName + "){\n");


            sf.append("        this." + filedName + "=" + filedName + ";\n");

            if (eq)
                sf.append("        setEqualField(" + entityName + "." + beanName.underscoreName(javaName).toUpperCase() + ", " + filedName + ");\n");
            else
                sf.append("        setLikeField(" + entityName + "." + beanName.underscoreName(javaName).toUpperCase() + ", " + filedName + ");\n");

            sf.append("        return this;\n");

            sf.append("    }\n");


            sf.append("    public String " + beanName.getMethodName(filedName) + "(){\n");

            sf.append("        return this." + filedName + ";\n");
            sf.append("    }\n\n");
        }

        return sf.toString();
    }


}
