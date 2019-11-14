package vip.sunke.template.controller.back;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.sunke.pubInter.IBaseService;
import vip.sunke.pubInter.RestfulController;
import vip.sunke.pubInter.exception.SkException;
import vip.sunke.template.dto.NewsDTO;
import vip.sunke.template.modelExt.NewsExt;
import vip.sunke.template.search.NewsSearch;
import vip.sunke.template.service.INewsService;
import vip.sunke.template.vo.NewsDetailVO;
import vip.sunke.template.vo.NewsListVO;
import vip.sunke.template.vo.NewsVO;

import javax.annotation.Resource;

/**
 * @author sunke
 * @Date 2019/11/13 14:38
 * @description
 */
@Api(tags = "招聘会信息", description = "招聘会信息接口")
@RestController
@RequestMapping("/back/newsApi/")

public class NewsApiController extends RestfulController<NewsDTO, NewsExt, NewsSearch, NewsVO, NewsDetailVO, NewsListVO> {


    @Resource(name = "newsService")
    private INewsService newsService;


    @Override
    public IBaseService getBaseService() throws SkException {
        return newsService;
    }

    @Override
    public String getBaseRoute() throws SkException {
        return "/back/newsApi/";
    }


}
