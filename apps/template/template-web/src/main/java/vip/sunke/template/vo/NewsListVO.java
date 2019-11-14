package vip.sunke.template.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.sunke.pubInter.AbstractPageVO;
import vip.sunke.pubInter.common.PageBean;

import java.util.List;

/**
*    @author sunke
*    @Date 2019-11-14 16:55:22
*    @description NewsListVO      新闻
*/

@Data
public class NewsListVO extends AbstractPageVO<NewsVO> {

    @ApiModelProperty("分页")
    private PageBean page;
    @ApiModelProperty("列表数据")
    private List<NewsVO> list;

}