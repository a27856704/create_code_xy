package vip.sunke.pubInter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sunke
 * @Date 2019/11/13 10:58
 * @description
 */
@Data
public abstract class AbstractDomainVO<KeyType> implements Serializable {

    @ApiModelProperty("id")
    private KeyType id;
}
