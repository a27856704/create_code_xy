package vip.sunke.template.dao;

import vip.sunke.template.modelExt.NewsExt;
import vip.sunke.template.search.NewsSearch;
import vip.sunke.pubInter.IBaseDao;


/**
 *    @author sunke
 *    @Date 2019-11-13 10:38:18
 *    @description NewsDao      新闻
 */
public interface INewsDao extends IBaseDao<NewsExt, NewsSearch,String> {

}