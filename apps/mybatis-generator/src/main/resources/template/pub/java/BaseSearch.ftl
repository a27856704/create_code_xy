package ${pubPackage}.pubInter;


import ${pubPackage}.common.StringUtil;
import ${pubPackage}.common.YXDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.*;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/
@ApiModel(value = "搜索条件",description = "搜索条件基类")
public abstract class BaseSearch implements Serializable {


    private static final long serialVersionUID = 1L;

    public static String DESC_ORDER = "desc";
    public static String ASC_ORDER = "asc";

    @ApiModelProperty(value = "排序字段",hidden = true)
    private String orderBy;// 排序字段
    @ApiModelProperty(value = "倒序，顺序",hidden = true)
    private String orderDesc = DESC_ORDER;// 倒序，顺序
    @ApiModelProperty(value = "排序字段",hidden = true)
    private String orderBy1;
    @ApiModelProperty(value = "倒序，顺序",hidden = true)
    private String orderDesc1 = DESC_ORDER;
    @ApiModelProperty(value = "排序字段",hidden = true)
    private String orderBy2;
    @ApiModelProperty(value = "倒序，顺序",hidden = true)
    private String orderDesc2 = DESC_ORDER;

    @ApiModelProperty(value = "创建开始时间")
    private Date createTimeStart;//开始时间
    @ApiModelProperty(value = "创建结束时间")
    private Date createTimeEnd;//结束时间

    @ApiModelProperty(value = "显示那个页面",hidden = true)
    private String showView;//显示那个页面
    @ApiModelProperty(hidden = true)
    private long start;
    @ApiModelProperty(hidden = true)
    private long limit = 10;

    @ApiModelProperty(value = "每页数量")
    private long pageSize=10;

    @ApiModelProperty(hidden = true)
    private long end = limit;
    @ApiModelProperty(hidden = true)
    private String customField;//自定义排序字段
    @ApiModelProperty(hidden = true)
    private String customOrder;//自定义排序顺序
    @ApiModelProperty(value = "当前页码",example = "1")
    private long pageNumber;//当前页面
    @ApiModelProperty(hidden = true)
    private String groupBy;
    @ApiModelProperty(hidden = true)
    private String defaultOrderField;//默認排序字段
    @ApiModelProperty(hidden = true)
    private Map<String, String> likeMap = null;
    @ApiModelProperty(hidden = true)
    private Map<String, String> equalMap = null;
    @ApiModelProperty(hidden = true)
    private Map<String, Integer> numberGreaterMap = null;//大於等於的
    @ApiModelProperty(hidden = true)
    private Map<String, Integer> numberLessMap = null;//小於等於的
    @ApiModelProperty(hidden = true)
    private Map<String, List<Object>> inMap;
    @ApiModelProperty(hidden = true)
    private Map<String, List<Object>> notInMap;
    @ApiModelProperty(hidden = true)
    private Map<String, Date> dateStartMap;//时间>=
    @ApiModelProperty(hidden = true)
    private Map<String, Date> dateEndMap;//<=
    private Map<String, Integer> bitMap;//位运算

   // private Map<String, Integer[]> arrayMap;

    public static String getDescOrder() {
        return DESC_ORDER;
    }

    public String getShowView() {
        return showView;
    }

    public void setShowView(String showView) {
        this.showView = showView;
    }

    public Map<String, Integer> getBitMap() {
        return bitMap;
    }

    public Map<String, Date> getDateEndMap() {
        return dateEndMap;
    }

    public Map<String, Date> getDateStartMap() {
        return dateStartMap;
    }

    public Map<String, String> getEqualMap() {
        return equalMap;
    }

    public Map<String, List<Object>> getInMap() {
        return inMap;
    }

    public Map<String, List<Object>> getNotInMap() {
        return notInMap;
    }

    public Map<String, Integer> getNumberGreaterMap() {
        return numberGreaterMap;
    }

    public Map<String, Integer> getNumberLessMap() {
        return numberLessMap;
    }

  /*  public Map<String, Integer[]> getArrayMap() {
        return arrayMap;
    }

    protected void setArrayMap(String field, Integer[] value) {
        if (value == null) {
            return;
        }
        if (arrayMap == null) {
            arrayMap = new HashMap<String, Integer[]>();
        }
        arrayMap.put(field, value);
    }*/

    protected void setBitField(String field, Integer value) {
        if (value == null)
            return;
        if (bitMap == null)
            bitMap = new HashMap<String, Integer>();

        bitMap.put(field, value);

    }


    protected void setDateEndField(String field, Date value) {

        if (value == null)
            return;
        if (dateEndMap == null)
            dateEndMap = new HashMap<String, Date>();


        dateEndMap.put(field, YXDate.getTimeDayLastSecond(value));

    }

    protected void setDateStartField(String field, Date value) {

        if (value == null)
            return;
        if (dateStartMap == null)
            dateStartMap = new HashMap<String, Date>();


        dateStartMap.put(field, YXDate.getTimeDayFirstSecond(value));

    }


    protected void setNotInField(String field, List<Object> value) {

        if (value == null)
            return;
        if (notInMap == null)
            notInMap = new HashMap<String, List<Object>>();


        addInMap(field, notInMap, value);


    }


    private void addInMap(String field, Map<String, List<Object>> map, List<Object> value) {

        List<Object> realValue = new ArrayList<Object>();

        value.forEach(o -> {
            if (o != null && !"".equalsIgnoreCase(o.toString()))
                realValue.add(o);
        });

        if (realValue.size() > 0)

            map.put(field, realValue);

    }


    protected void setInField(String field, List<Object> value) {
        if (value == null)
            return;
        if (inMap == null)
            inMap = new HashMap<String, List<Object>>();

        addInMap(field, inMap, value);

    }


    protected void setLessField(String field, int value) {


        if (numberLessMap == null)
            numberLessMap = new HashMap<String, Integer>();


        numberLessMap.put(field, value);

    }


    protected void setGreaterField(String field, int value) {
        if (numberGreaterMap == null)
            numberGreaterMap = new HashMap<String, Integer>();
        numberGreaterMap.put(field, value);

    }


    protected void setEqualField(String field, String value) {

        if (StringUtil.isNullOrEmpty(value))
            return;


        if (equalMap == null)
            equalMap = new HashMap<String, String>();
        equalMap.put(field, value.trim());


    }


    protected void setLikeField(String field, String value) {

        if (StringUtil.isNullOrEmpty(value))
            return;


        if (likeMap == null)
            likeMap = new HashMap<String, String>();
        likeMap.put(field, value.trim());


    }


    public Map<String, String> getLikeMap() {
        return likeMap;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getDefaultOrderField() {

        defaultOrderField = setDefaultField();

        return defaultOrderField;
    }

    public abstract String setDefaultField();


    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
        setStart();
    }

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public String getCustomOrder() {
        return customOrder;
    }

    public void setCustomOrder(String customOrder) {
        this.customOrder = customOrder;
    }

    public String getOrderBy1() {
        return orderBy1;
    }

    public void setOrderBy1(String orderBy1) {
        this.orderBy1 = orderBy1;
    }

    public String getOrderDesc1() {
        return orderDesc1;
    }

    public void setOrderDesc1(String orderDesc1) {
        this.orderDesc1 = orderDesc1;
    }

    public String getOrderBy2() {
        return orderBy2;
    }

    public void setOrderBy2(String orderBy2) {
        this.orderBy2 = orderBy2;
    }

    public String getOrderDesc2() {
        return orderDesc2;
    }

    public void setOrderDesc2(String orderDesc2) {
        this.orderDesc2 = orderDesc2;
    }

    public long getStart() {

        return start;
    }

    private void setStart() {

        long offset = limit * (pageNumber - 1);
        if (offset <= 0)
            offset = 0;

        this.start = offset;
        this.end = offset + limit;

    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
        setStart();

    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
        setLimit(pageSize);

    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    @Override
    public String toString() {
        return ",BaseSearch{" +
                "orderBy='" + orderBy + '\'' +
                ", orderDesc='" + orderDesc + '\'' +
                ", orderBy1='" + orderBy1 + '\'' +
                ", orderDesc1='" + orderDesc1 + '\'' +
                ", orderBy2='" + orderBy2 + '\'' +
                ", orderDesc2='" + orderDesc2 + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", groupBy='" + groupBy + '\'' +
                '}';
    }
}
