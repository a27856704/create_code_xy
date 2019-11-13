package vip.sunke.web.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * http response 响应map
 *
 * @author sunke
 * @Date 2017/6/9 14:26
 * @description
 */
@ApiModel("数据响应")
public class SkMap implements Serializable {

    private static final String MESSAGE = "message";
    private static final String ERROR_CODE = "errorCode";
    private static final String STATUS_OK = "success";
    private static final String MESSAGE_OK = "操作成功";
    private static final String MESSAGE_FAIL = "操作失败";
    private static final String DATA = "data";

    @ApiModelProperty(value = "描述")
    private String message;
    @ApiModelProperty(value = "错误码")
    private int errorCode;
    @ApiModelProperty(value = "是否成功")
    private boolean success;
    @ApiModelProperty(value = "数据结果集")
    private Map data;

    public SkMap() {
        data = new HashMap();

    }

    public static SkMap ok() {
        return ok(MESSAGE_OK);
    }

    public static SkMap ok(String message) {
        return new SkMap().setOk(message);
    }

    public static SkMap fail() {
        return fail(MESSAGE_FAIL);
    }

    public static SkMap fail(String message) {
        return new SkMap().setFail(message);
    }

    public static SkMap result(Object key, Object value, String message) {
        return new SkMap().setResult(key, value, message);
    }

    public static SkMap ok(Object key, Object value) {
        return ok().set(key, value);
    }

    public static SkMap fail(Object key, Object value) {
        return fail().set(key, value);
    }

    public static SkMap create() {
        return new SkMap();
    }

    public static SkMap create(Object key, Object value) {
        return new SkMap().set(key, value);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public SkMap setResult(Object key, Object value, String message) {


        setSuccess(Boolean.TRUE);
        setErrorCode(0);
        setMessage(message);
        set(key, value);
        return this;
    }

    public SkMap setResult(String message, boolean suss) {

        setMessage(message);
        setSuccess(suss);
        setErrorCode(suss ? 0 : -1);
        return this;
    }


    public SkMap setOk(String message) {
        return setResult(message, true);
    }


    public SkMap setFail(String message) {
        return setResult(message, false);
    }

    public SkMap set(Object key, Object value) {
        if (data == null)
            data = new HashMap();
        data.put(key, value);
        return this;
    }

    public <T> T getValueByData(String key) {

        return (T) data.get(key);
    }


    public SkMap set(Map map) {
        data.putAll(map);
        return this;
    }


    public SkMap delete(Object key) {
        data.remove(key);
        return this;
    }

    public <T> T getAs(Object key) {
        return (T) data.get(key);
    }

    public String getStr(Object key) {
        return (String) data.get(key);
    }

    public Integer getInt(Object key) {
        return (Integer) data.get(key);
    }

    public Long getLong(Object key) {
        return (Long) data.get(key);
    }

    public Boolean getBoolean(Object key) {
        return (Boolean) data.get(key);
    }

    /**
     * key 存在，并且 value 不为 null
     */
    public boolean notNull(Object key) {
        return data.get(key) != null;
    }

    /**
     * key 不存在，或者 key 存在但 value 为null
     */
    public boolean isNull(Object key) {
        return data.get(key) == null;
    }

    /**
     * key 存在，并且 value 为 true，则返回 true
     */
    public boolean isTrue(Object key) {
        Object value = data.get(key);
        return (value instanceof Boolean && ((Boolean) value == true));
    }

    /**
     * key 存在，并且 value 为 false，则返回 true
     */
    public boolean isFalse(Object key) {
        Object value = data.get(key);
        return (value instanceof Boolean && ((Boolean) value == false));
    }


    public boolean equals(Object skMap) {
        return skMap instanceof SkMap && super.equals(skMap);
    }


}
