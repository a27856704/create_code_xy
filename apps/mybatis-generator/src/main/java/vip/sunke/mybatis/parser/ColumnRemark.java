package vip.sunke.mybatis.parser;


import vip.sunke.mybatis.InputTypeEnum;
import vip.sunke.mybatis.NumberUtil;
import vip.sunke.mybatis.SearchTypeEnum;
import vip.sunke.mybatis.YesOrNoEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunke
 * @Date 2019-04-28 10:07
 * @description
 */

public class ColumnRemark {

    private String descName;//名称
    private int searchFlag;//搜索
    private SearchTypeEnum searchTypeEnum;
    private boolean show;//显示
    private boolean showListPage;//列表时显示
    private boolean need;//必选
    private int inputType;//输入框
    private InputTypeEnum inputTypeEnum;
    private String inputTag;
    private List<ColumnValue> valueList;//输入的值
    private String defaultValue;//默认值
    private String name;//类字段名称
    private String dbName;//数据库对应字段
    private String jdbcType;//数据库类型
    private String javaType;
    private int totalValue;
    private String entityName;
    private String valueString;//输入的值

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;

    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public SearchTypeEnum getSearchTypeEnum() {
        return searchTypeEnum;
    }

    public void setSearchTypeEnum(SearchTypeEnum searchTypeEnum) {
        this.searchTypeEnum = searchTypeEnum;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public boolean isShowListPage() {
        return showListPage;
    }

    public void setShowListPage(boolean showListPage) {
        this.showListPage = showListPage;
    }

    // private String inputHtml;

    public InputTypeEnum getInputTypeEnum() {
        return inputTypeEnum;
    }

    public void setInputTypeEnum(InputTypeEnum inputTypeEnum) {
        this.inputTypeEnum = inputTypeEnum;
    }


    //名称|搜索|添加或修改显示|列表显示|必选|输入框类型|1@是,0@否
    public void parse(String remark) {

        if (remark == null || "".equalsIgnoreCase(remark))
            return;


        String[] columnArr = remark.split("\\|");

        int size = columnArr.length;
        int currentIndex = 0;

        if (size > currentIndex) {//名称
            setDescName(columnArr[currentIndex]);
        }
        currentIndex = currentIndex + 1;

        if (size > currentIndex) {//搜索
            setSearchFlag(NumberUtil.parseInt(columnArr[currentIndex], SearchTypeEnum.LIKE.getType()));
        }
        currentIndex = currentIndex + 1;

        if (size > currentIndex) {//添加或修改显示
            setShow(NumberUtil.parseInt(columnArr[currentIndex], YesOrNoEnum.YES.getType()) == YesOrNoEnum.YES.getType());
        }
        currentIndex = currentIndex + 1;


        if (size > currentIndex) {//列表显示
            setShowListPage(NumberUtil.parseInt(columnArr[currentIndex], YesOrNoEnum.YES.getType()) == YesOrNoEnum.YES.getType());
        }
        currentIndex = currentIndex + 1;


        if (size > currentIndex) {//必选
            setNeed(NumberUtil.parseInt(columnArr[currentIndex], YesOrNoEnum.YES.getType()) == YesOrNoEnum.YES.getType());
        }
        currentIndex = currentIndex + 1;


        if (size > currentIndex) {//输入框类型
            setInputType(NumberUtil.parseInt(columnArr[currentIndex], InputTypeEnum.TEXT.getType()));


        }
        currentIndex = currentIndex + 1;


        if (size > currentIndex) {//值

            List<ColumnValue> valueList = new ArrayList<ColumnValue>();


            setValueString(columnArr[currentIndex]);

            String[] valueArr = columnArr[currentIndex].split("\\,");


            ColumnValue columnValue = null;
            if (valueArr != null && valueArr.length > 0) {
                String[] oneValueArr = null;
                for (String value : valueArr) {
                    if (value == null || "".equalsIgnoreCase(value))
                        continue;
                    oneValueArr = value.split("@");

                    columnValue = new ColumnValue(oneValueArr[0], oneValueArr[0], getName()+"_"+oneValueArr[0].replace("-","_"));
                    if (oneValueArr.length > 1) {
                        columnValue.setDesc(oneValueArr[1]);
                    }
                    if (oneValueArr.length > 2) {
                        columnValue.setEnName(oneValueArr[2]);
                    }
                    valueList.add(columnValue);
                    totalValue = totalValue + NumberUtil.parseInt(oneValueArr[0]);
                }
            }
            setValueList(valueList);


        }


    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDescName() {
        return descName;
    }

    public void setDescName(String descName) {
        this.descName = descName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSearchFlag() {
        return searchFlag;
    }

    public void setSearchFlag(int searchFlag) {
        this.searchFlag = searchFlag;
        this.searchTypeEnum = SearchTypeEnum.getEnumByType(searchFlag);
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isNeed() {
        return need;
    }

    public void setNeed(boolean need) {
        this.need = need;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
        inputTypeEnum = InputTypeEnum.getEnumByType(inputType);
        this.inputTag = inputTypeEnum.getTag();
    }

    public String getInputTag() {
        return inputTag;
    }

    public void setInputTag(String inputTag) {
        this.inputTag = inputTag;
    }

    public List<ColumnValue> getValueList() {
        return valueList;
    }

    public void setValueList(List<ColumnValue> valueList) {
        this.valueList = valueList;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


}
