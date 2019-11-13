package vip.sunke.web.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vip.sunke.common.StringUtil;

import java.io.Serializable;

/**
 * @author sunke
 * @Date 2019/1/9 09:26
 * @description
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultData<T> implements Serializable {

    private static final String MESSAGE = "message";
    private static final String ERROR_CODE = "errorCode";
    private static final String STATUS_OK = "success";
    private static final String MESSAGE_OK = "操作成功";
    private static final String MESSAGE_FAIL = "操作失败";
    private static final String DATA = "data";

    @ApiModelProperty(value = "错误码描述", name = "错误码描述")
    private String message;

    @ApiModelProperty(value = "是否成功", name = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "错误码", name = "错误码")
    private int errorCode;

    @ApiModelProperty(value = "数据对象", name = "数据对象")
    private T data;

    @Override
    public String toString() {
        return "ResultData{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", errorCode=" + errorCode +
                '}';
    }

    public static ResultData ok() {

        return ok(null, null);

    }


    public static ResultData ok(String key, Object o) {

        ResultData resultData = new ResultData();

        resultData.setMessage(MESSAGE_OK);
        resultData.setErrorCode(0);
        resultData.setSuccess(true);
        if (!StringUtil.isNullOrEmpty(key))

           resultData.setData(o);

        return resultData;


    }


    public T getData() {

        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
