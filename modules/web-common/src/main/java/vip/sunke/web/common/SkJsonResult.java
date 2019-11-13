package vip.sunke.web.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * http response 响应map
 *
 * @author sunke
 * @Date 2017/6/9 14:26
 * @description
 */
@ApiModel("数据响应")
public class SkJsonResult<T> implements Serializable {

    private static final String MESSAGE_OK = "操作成功";
    private static final String MESSAGE_FAIL = "操作失败";

    @ApiModelProperty(value = "描述")
    private String message = MESSAGE_OK;
    @ApiModelProperty(value = "错误码")
    private int errorCode = 0;
    @ApiModelProperty(value = "是否成功")
    private boolean success = true;
    @ApiModelProperty(value = "数据结果集")
    private T data;

    public SkJsonResult() {



    }

    public static SkJsonResult ok() {
        return ok(MESSAGE_OK);
    }

    public static SkJsonResult ok(String message) {
        return new SkJsonResult().setOk(message);
    }

    public static SkJsonResult fail() {
        return fail(MESSAGE_FAIL);
    }

    public static SkJsonResult fail(String message) {
        return new SkJsonResult().setFail(message);
    }


    public static SkJsonResult create() {
        return new SkJsonResult();
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public SkJsonResult setResult(String message, boolean suss) {

        setMessage(message);
        setSuccess(suss);
        setErrorCode(suss ? 0 : -1);
        return this;
    }


    public SkJsonResult setOk(String message) {
        return setResult(message, true);
    }


    public SkJsonResult setFail(String message) {
        return setResult(message, false);
    }


    public boolean equals(Object skMap) {
        return skMap instanceof SkJsonResult && super.equals(skMap);
    }


}
