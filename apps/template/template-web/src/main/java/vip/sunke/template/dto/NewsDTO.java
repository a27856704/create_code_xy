package vip.sunke.template.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.sunke.pubInter.AbstractDTO;

/**
 * @author sunke
 * @Date 2019-11-13 10:38:18
 * @description NewsDTO      新闻
 */
@ApiModel("新闻")
@Data
public class NewsDTO extends AbstractDTO {

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
}
