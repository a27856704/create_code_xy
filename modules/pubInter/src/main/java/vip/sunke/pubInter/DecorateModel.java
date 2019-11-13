package vip.sunke.pubInter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author sunke
 * @Date 2019/11/13 14:48
 * @description
 */
@Data
@AllArgsConstructor
@ApiModel("domain")
public class DecorateModel<T> {



    @ApiModelProperty("domain")
    private T domain;
}
