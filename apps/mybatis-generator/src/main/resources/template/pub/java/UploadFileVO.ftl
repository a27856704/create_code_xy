package ${pubPackage}.pubInter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ${author}
 * @Date ${createTime}
 * @description 前台上传文件返回结果集
 */
@Data
@ApiModel("上传图片返回")
public class UploadFileVO implements Serializable {

    @ApiModelProperty("域名")
    private String website;
    @ApiModelProperty("图片路径")
    private String img;
}
