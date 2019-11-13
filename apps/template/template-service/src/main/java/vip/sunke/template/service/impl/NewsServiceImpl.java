package vip.sunke.template.service.impl;

import vip.sunke.template.modelExt.NewsExt;
import vip.sunke.template.dao.INewsDao;
import vip.sunke.template.search.NewsSearch;
import vip.sunke.pubInter.IBaseDao;
import vip.sunke.pubInter.AbstractBaseService;
import vip.sunke.template.service.INewsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
*    @author sunke
*    @Date 2019-11-13 10:38:18
*    @description NewsService      新闻
*/
@Service(value = "newsService")
public class NewsServiceImpl extends AbstractBaseService<NewsExt, NewsSearch,String> implements INewsService {

    @Resource(name = "newsDao")
    private INewsDao newsDao;

    @Override
    public IBaseDao<NewsExt, NewsSearch,String> getDao() {
        return newsDao;
    }
}