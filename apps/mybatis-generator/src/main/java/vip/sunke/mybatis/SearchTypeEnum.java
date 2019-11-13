package vip.sunke.mybatis;

/**
 * @author sunke
 * @Date 2019-04-28 09:20
 * @description 搜索条件相关
 */

public enum SearchTypeEnum {


    LIKE(1, "LIKE", "Like", "Like"),
    EQUAL(2, "EQUAL", "Eq", "Eq"),
    NUMBER(3, "大于小于", "Greater", "Less"),
    IN(4, "IN", "In", "In"),
    NOT_IN(5, "NOT IN", "NotIn", "NotIn"),
    DATE(6, "时间", "Start", "End"),
    BIT(7, "位运算", "Bit", "Bit");


    private int type;
    private String desc;
    private String suffixStart;
    private String suffixEnd;

    public String getSuffixStart() {
        return suffixStart;
    }

    public void setSuffixStart(String suffixStart) {
        this.suffixStart = suffixStart;
    }

    public String getSuffixEnd() {
        return suffixEnd;
    }

    public void setSuffixEnd(String suffixEnd) {
        this.suffixEnd = suffixEnd;
    }

    SearchTypeEnum(int type, String desc, String suffixStart, String suffixEnd) {
        this.type = type;
        this.desc = desc;
        this.suffixStart = suffixStart;
        this.suffixEnd = suffixEnd;

    }

    public static SearchTypeEnum getEnumByType(int type) {

        for (SearchTypeEnum searchTypeEnum : SearchTypeEnum.values()) {

            if (searchTypeEnum.type == type)
                return searchTypeEnum;

        }

        return LIKE;
    }

    public static String getDescByType(int type) {

        for (SearchTypeEnum searchTypeEnum : SearchTypeEnum.values()) {

            if (searchTypeEnum.type == type)
                return searchTypeEnum.getDesc();

        }

        return "无";
    }

    public static String getSuffixByType(int type, boolean start) {

        for (SearchTypeEnum searchTypeEnum : SearchTypeEnum.values()) {

            if (searchTypeEnum.type == type) {
                if (start) {
                    return searchTypeEnum.getSuffixStart();
                }
                return searchTypeEnum.getSuffixEnd();
            }


        }
        return LIKE.getSuffixStart();


    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
