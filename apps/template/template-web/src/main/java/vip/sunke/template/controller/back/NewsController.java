package vip.sunke.template.controller.back;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.sunke.pubInter.IBaseService;
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
 * @Date 2019-11-14 16:57:51
 * @description NewsController      新闻
 */
@Api(tags = "新闻相关", description = "新闻相关接口")
@RestController(value = "newsRestfulController")
@RequestMapping("/back/news/")
public class NewsController extends ApiManageController<
        NewsDTO
        , NewsExt
        , NewsSearch
        , NewsVO
        , NewsDetailVO
        , NewsListVO> {

    @Resource(name = "newsService")
    private INewsService newsService;

    @Override
    public IBaseService<NewsExt, NewsSearch, String> getBaseService() throws SkException {
        return newsService;
    }

    @Override
    public String getBaseRoute() {
        return "/back/news/";
    }





}