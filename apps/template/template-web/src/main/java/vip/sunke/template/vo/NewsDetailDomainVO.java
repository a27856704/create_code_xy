package vip.sunke.template.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
*    @author sunke
*    @Date 2019-12-09 14:43:58
*    @description NewsDetailDomainVO      新闻
*/

@Data
@Accessors(chain = true)
@ApiModel("新闻详情")
public class NewsDetailDomainVO extends NewsDomainVO {

}