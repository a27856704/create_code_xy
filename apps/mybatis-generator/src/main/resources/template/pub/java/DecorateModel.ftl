package ${pubPackage}.pubInter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/
@Data
@AllArgsConstructor
@ApiModel("domain")
public class DecorateModel<T> {


    @ApiModelProperty("domain")
    private T domain;
}
