package vip.sunke.template.dao.impl;

import vip.sunke.template.modelExt.NewsExt;
import vip.sunke.template.search.NewsSearch;
import vip.sunke.pubInter.AbstractBaseDao;
import vip.sunke.template.dao.mapperExt.NewsMapperExt;
import vip.sunke.template.dao.INewsDao;
import vip.sunke.pubInter.IBaseMapper;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/**
 *    @author sunke
 *    @Date 2019-11-13 10:38:18
 *    @description NewsDaoImpl      新闻
 */
@Repository(value = "newsDao")
public class NewsDaoImpl extends AbstractBaseDao<NewsExt, NewsSearch,String> implements INewsDao {

    @Resource(name = "newsMapperExt")
    private NewsMapperExt newsMapperExt;

    @Override
    public IBaseMapper<NewsExt,  NewsSearch,String> getMapper() {
        return newsMapperExt;
    }
}