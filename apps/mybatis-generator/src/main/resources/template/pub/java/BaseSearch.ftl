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
@ApiModel(value = "搜索条件", description = "搜索条件基类")
public abstract class BaseSearch implements Serializable {


    private static final long serialVersionUID = 1L;
    public static String DESC_ORDER = "desc";
    public static String ASC_ORDER = "asc";
    @ApiModelProperty("自定义sql")
    private String customSql;
    @ApiModelProperty(value = "排序字段", hidden = true)
    private String orderBy;// 排序字段
    @ApiModelProperty(value = "倒序，顺序", hidden = true)
    private String orderDesc = DESC_ORDER;// 倒序，顺序
    @ApiModelProperty(value = "排序字段", hidden = true)
    private String orderBy1;
    @ApiModelProperty(value = "倒序，顺序", hidden = true)
    private String orderDesc1 = DESC_ORDER;
    @ApiModelProperty(value = "排序字段", hidden = true)
    private String orderBy2;
    @ApiModelProperty(value = "倒序，顺序", hidden = true)
    private String orderDesc2 = DESC_ORDER;
    @ApiModelProperty(value = "创建开始时间")
    private Date createTimeStart;//开始时间
    @ApiModelProperty(value = "创建结束时间")
    private Date createTimeEnd;//结束时间
    @ApiModelProperty(value = "更新开始时间")
    private Date updateTimeStart;//开始时间
    @ApiModelProperty(value = "更新结束时间")
    private Date updateTimeEnd;//结束时间
    @ApiModelProperty(value = "显示那个页面", hidden = true)
    private String showView;//显示那个页面
    @ApiModelProperty(hidden = true)
    private long start;
    @ApiModelProperty(hidden = true)
    private long limit = 15;
    @ApiModelProperty(value = "每页数量", example = "15")
    private long pageSize = 15;
    @ApiModelProperty(hidden = true)
    private long end = limit;
    @ApiModelProperty(hidden = true)
    private String customField;//自定义排序字段
    @ApiModelProperty(hidden = true)
    private String customOrder;//自定义排序顺序
    @ApiModelProperty(value = "当前页码", example = "1")
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
    private Map<String, Object> numberGreaterMap = null;//大於等於的
    @ApiModelProperty(hidden = true)
    private Map<String, Object> numberLessMap = null;//小於等於的
    @ApiModelProperty(hidden = true)
    private Map<String, List<Object>> inMap;
    @ApiModelProperty(hidden = true)
    private Map<String, List<Object>> notInMap;
    @ApiModelProperty(hidden = true)
    private Map<String, Date> dateStartMap;//时间>=
    @ApiModelProperty(hidden = true)
    private Map<String, Date> dateEndMap;//<=
    private Map<String, Integer> bitMap;//位运算
    private Map<String, List<Integer>> notBitMap;//((field & value)!=value)位运算
    private Map<String, Integer> orBitMap;//位运算 field|value==value（包含）
    @ApiModelProperty(hidden = true)
    private Set<String> nullSet;//为null
    @ApiModelProperty(hidden = true)
    private Set<String> notNullSet; //不为null
    /**
     * 用来保持是否附加其实数据
     */
    private Set<String> fullConfigSet;
    /**
     * 要排除的附加数据
     */
    private Set<String> unFullConfigSet;

    public static String getDescOrder() {
        return DESC_ORDER;
    }

    public String getCustomSql() {
        return customSql;
    }

    public void setCustomSql(String customSql) {
        this.customSql = customSql;
    }

    public Set<String> getUnFullConfigSet() {
        return unFullConfigSet;
    }

    public void setUnFullConfigSet(Set<String> unFullConfigSet) {
        this.unFullConfigSet = unFullConfigSet;
    }

    public Set<String> getFullConfigSet() {
        return fullConfigSet;
    }

    public void setFullConfigSet(Set<String> fullConfigSet) {
        this.fullConfigSet = fullConfigSet;
    }

    public void putUnFullConfigSet(String key) {
        if (StringUtil.isNullOrEmpty(unFullConfigSet)) {
            unFullConfigSet = new HashSet<>();
        }
        unFullConfigSet.add(key);
    }

    public void putFullConfigSet(String key) {
        if (StringUtil.isNullOrEmpty(fullConfigSet)) {
            fullConfigSet = new HashSet<>();
        }
        fullConfigSet.add(key);
    }


    public Map<String, List<Integer>> getNotBitMap() {
        return notBitMap;
    }

   /* public void setNotBitMap(Map<String, Integer> notBitMap) {
        this.notBitMap = notBitMap;
    }*/

    public Set<String> getNotNullSet() {
        return notNullSet;
    }

    public Set<String> getNullSet() {
        return nullSet;
    }

    public void setFieldNotNull(String field) {

        if (StringUtil.isNullOrEmpty(field)) {
            return;
        }
        if (notNullSet == null) {
            notNullSet = new HashSet<>();
        }
        notNullSet.add(field);

    }

    public void setFieldNull(String field) {

        if (StringUtil.isNullOrEmpty(field)) {
            return;
        }
        if (nullSet == null) {
            nullSet = new HashSet<>();
        }
        nullSet.add(field);
    }

    public Date getUpdateTimeStart() {
        return updateTimeStart;
    }

    public void setUpdateTimeStart(Date updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
        if (StringUtil.isNotEmpty(updateTimeFiled())) {
            setDateStartField(updateTimeFiled(), updateTimeStart);

        }

    }

    public Date getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    // private Map<String, Integer[]> arrayMap;

    public void setUpdateTimeEnd(Date updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
        if (StringUtil.isNotEmpty(updateTimeFiled())) {
            setDateEndField(updateTimeFiled(), updateTimeEnd);
        }

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

    public Map<String, Integer> getOrBitMap() {
        return orBitMap;
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

    public Map<String, Object> getNumberGreaterMap() {
        return numberGreaterMap;
    }

    public Map<String, Object> getNumberLessMap() {
        return numberLessMap;
    }

  /*  public Map<String, Integer[]> getArrayMap() {
        return arrayMap;
    }

    public void setArrayMap(String field, Integer[] value) {
        if (value == null) {
            return;
        }
        if (arrayMap == null) {
            arrayMap = new HashMap<String, Integer[]>();
        }
        arrayMap.put(field, value);
    }*/

    public void setBitField(String field, Integer value) {

        if (bitMap == null) {
            bitMap = new HashMap<String, Integer>();
        }

        if (value == null) {
            bitMap.remove(field);

        } else {
            bitMap.put(field, value);
        }


    }

    public void setNotBitField(String field, Integer value) {

        if (notBitMap == null) {
            notBitMap = new HashMap<String, List<Integer>>();
        }

        if (value == null) {
            notBitMap.remove(field);

        } else {
            if (notBitMap.containsKey(field)) {
                notBitMap.get(field).add(value);
            } else {
                notBitMap.put(field, SkList.getInstance().addObjToList(value));
            }


        }


    }

    public void setOrBitField(String field, Integer value) {

        if (orBitMap == null) {
            orBitMap = new HashMap<String, Integer>();
        }

        if (value == null) {
            orBitMap.remove(field);

        } else {
            orBitMap.put(field, value);
        }


    }


    public void setDateEndField(String field, Date value) {

        if (value == null) {
            if (dateEndMap != null) {
                dateEndMap.remove(field);
            }
            return;
        }
        if (dateEndMap == null) {
            dateEndMap = new HashMap<String, Date>();
        }


        dateEndMap.put(field, value);

    }

    public void setDateStartField(String field, Date value) {

        if (value == null) {
            if (dateStartMap != null) {
                dateStartMap.remove(field);
            }
            return;
        }
        if (dateStartMap == null) {
            dateStartMap = new HashMap<String, Date>();
        }


        dateStartMap.put(field, value);

    }


    public void setNotInField(String field, List<Object> value) {

        if (value == null) {

            if (notInMap != null) {
                notInMap.remove(field);
            }
            return;
        }
        if (notInMap == null) {
            notInMap = new HashMap<String, List<Object>>();
        }


        addInMap(field, notInMap, value);


    }


    private void addInMap(String field, Map<String, List<Object>> map, List<Object> value) {

        List<Object> realValue = new ArrayList<Object>();

        value.forEach(o -> {
            if (o != null && !"".equalsIgnoreCase(o.toString())) {
                realValue.add(o);
            }
        });

        if (realValue.size() > 0) {
            map.put(field, realValue);
        }

    }


    public void setInField(String field, List<Object> value) {
        if (value == null) {
            if (inMap != null) {
                inMap.remove(field);
            }
            return;
        }
        if (inMap == null) {
            inMap = new HashMap<String, List<Object>>();
        }

        addInMap(field, inMap, value);

    }


    public void setLessField(String field, Object value) {


        if (numberLessMap == null) {
            numberLessMap = new HashMap<String, Object>();
        }


        numberLessMap.put(field, value);

    }


    public void setGreaterField(String field, Object value) {
        if (numberGreaterMap == null) {
            numberGreaterMap = new HashMap<String, Object>();
        }
        numberGreaterMap.put(field, value);

    }


    public void setEqualField(String field, Object value) {

        if (StringUtil.isNullOrEmpty(value)) {
            if (equalMap != null) {
                equalMap.remove(field);
            }
            return;
        }

        if (equalMap == null) {
            equalMap = new HashMap<String, String>();
        }
        equalMap.put(field, StringUtil.toString(value).trim());


    }


    public void setLikeField(String field, Object value) {

        if (StringUtil.isNullOrEmpty(value)) {
            if (likeMap != null) {
                likeMap.remove(field);
            }
            return;
        }

        if (likeMap == null) {
            likeMap = new HashMap<String, String>();
        }
        likeMap.put(field, StringUtil.toString(value).trim());


    }


    public Map<String, String> getLikeMap() {
        return likeMap;
    }


    public String createTimeFiled() {
        return "";
    }

    public String updateTimeFiled() {
        return "";
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
        if (StringUtil.isNotEmpty(createTimeFiled())) {
            setDateStartField(createTimeFiled(), createTimeStart);
        }
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
        if (StringUtil.isNotEmpty(createTimeFiled())) {
            setDateEndField(createTimeFiled(), createTimeEnd);
        }


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

    public abstract String pkField();


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
        if (offset <= 0) {
            offset = 0;
        }

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
}