package vip.sunke.template.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import vip.sunke.pubInter.AbstractPageVO;

/**
*    @author sunke
*    @Date 2019-12-09 14:43:58
*    @description NewsListVO      新闻
*/

@Data
@Accessors(chain = true)
public class NewsListVO extends AbstractPageVO<NewsDomainVO> {

}