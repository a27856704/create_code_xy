package vip.sunke.template.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import vip.sunke.pubInter.IBaseService;
import vip.sunke.pubInter.exception.SkException;
import vip.sunke.template.modelExt.NewsExt;
import vip.sunke.template.search.NewsSearch;
import vip.sunke.template.service.INewsService;

import javax.annotation.Resource;

/**
*    @author sunke
*    @Date 2019-11-13 10:38:18
*    @description NewsController      新闻
*/

@Controller(value ="newsBackController")
@RequestMapping("/back/news/")
public class NewsController extends BackManageController<NewsExt, NewsSearch,String> {

	@Resource(name = "newsService")
	private INewsService newsService;

	@Override
	public IBaseService<NewsExt, NewsSearch,String> getBaseService() throws SkException {
		return newsService;
	}

	@Override
	public String getBaseRoute() {
		return "/back/news/";
	}

	@Override
	public String getBaseView() {
		return "back/news/";
	}



	@Override
	public String getMenuModel() {
		return "新闻管理";
	}

}