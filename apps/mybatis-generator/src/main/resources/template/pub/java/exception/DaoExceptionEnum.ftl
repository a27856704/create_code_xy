package ${pubPackage}.pubInter.exception;

/**
* @author ${author}
* @Date ${createTime}
* @description dao异常枚举
*/

public enum DaoExceptionEnum implements ExceptionEnum<DaoExceptionEnum> {

    DAO_GET_ERROR(1000, "dao.get.error", "查无数据"),
    DAO_INSERT_ERROR(1005, "dao.insert.error", "添加数据出错"),
    DAO_INSERT_OVER(1010, "dao.insert.over", "添加数据超过限制条数"),
    DAO_UPDATE_ERROR(1015, "dao.update.error", "更新数据出错"),
    DAO_DEL_ERROR(1020, "dao.del.error", "删除数据出错"),
    DAO_LIST_ERROR(1025, "dao.list.error", "列表数据出错"),
    DAO_LIST_COUNT_ERROR(1030, "dao.list.count.error", "列表记录数出错"),
    DAO_DETAIL_ERROR(1035, "dao.detail.error", "详情出错");




    private int code;
    private String key;
    private String message;

    DaoExceptionEnum(int code, String key, String message) {
        this.code = code;
        this.key = key;
        this.message = message;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMessage() {
        return message;
    }




}
