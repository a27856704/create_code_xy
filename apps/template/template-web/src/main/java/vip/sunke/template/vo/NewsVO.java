package vip.sunke.template.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.sunke.pubInter.AbstractVO;

/**
*    @author sunke
*    @Date 2019-11-13 10:38:18
*    @description NewsVO      新闻
*/
@Data
@ApiModel("新闻")
public class NewsVO extends AbstractVO<String> {



    @ApiModelProperty("标题")
    private String title;


}
