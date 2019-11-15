package vip.sunke.template.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import vip.sunke.pubInter.AbstractDomainVO;

/**
*    @author sunke
*    @Date 2019-11-14 22:15:43
*    @description NewsVO      新闻
*/

@Data
@ApiModel("新闻")
public class NewsVO extends AbstractDomainVO<String> {

}