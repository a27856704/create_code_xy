package vip.sunke.template.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.sunke.pubInter.AbstractDomainVO;

/**
*    @author sunke
*    @Date 2019-11-14 16:55:22
*    @description NewsVO      新闻
*/

@Data
@ApiModel("新闻")
public class NewsVO extends AbstractDomainVO<String> {
    @ApiModelProperty("标题")
    private String title;

}