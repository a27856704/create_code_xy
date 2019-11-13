package vip.sunke.pubInter;

import vip.sunke.pubInter.common.PageBean;
import vip.sunke.pubInter.common.PubConst;
import vip.sunke.pubInter.exception.BusinessException;
import vip.sunke.pubInter.exception.DaoException;
import vip.sunke.pubInter.exception.DaoExceptionEnum;
import vip.sunke.pubInter.exception.SkException;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunke
 * @Date 2017/6/9 14:32
 * @description
 */

public abstract class AbstractBaseFindService<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> {


    public abstract IBaseFindDao<T, TS, KeyType> getDao();

    /**
     * 返回搜索类Class
     *
     * @return
     */
    public Class<TS> getSearchClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[1];
    }


    public Class<T> getDomainClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }


    public T getDetail(KeyType id) throws SkException {
        T result = getDao().getDetail(id);
        if (result == null)
            throw new DaoException(DaoExceptionEnum.DAO_GET_ERROR);
        return result;
    }

    public List<T> getList(TS search) throws SkException {
        if (search == null)
            try {
                return getDao().getList(getSearchClass().newInstance());
            } catch (Exception e) {
                return null;
            }
        return getDao().getList(search);
    }

    public int getListCount(TS search) throws SkException {
        if (search == null)
            try {
                return getDao().getListCount(getSearchClass().newInstance());
            } catch (Exception e) {
                return 0;
            }
        return getDao().getListCount(search);
    }

    public List<T> getAllList(TS search) throws SkException {
        if (search == null) {
            try {
                return getDao().getAllList(getSearchClass().newInstance());
            } catch (Exception e) {
                return null;
            }
        }
        return getDao().getAllList(search);
    }


    /**
     * 分頁
     *
     * @param search
     * @return
     * @throws BusinessException key = "'86400#'+#root.targetClass+'.page.'+#search.toString()"
     */
    public Map<String, Object> paginate(TS search) throws SkException {
        Map<String, Object> resultMap = new HashMap<>();
        if (search == null) {
            try {
                search = getSearchClass().newInstance();
            } catch (Exception e) {
                resultMap.put(PubConst.PAGINATE_PAGE_KEY, null);
                resultMap.put(PubConst.PAGINATE_DATA_KEY, null);
                return resultMap;
            }
        }
        PageBean page = new PageBean();
        page.setPageSize(search.getLimit());
        page.setTotalCount(getDao().getListCount(search));
        long totalPage = page.getTotalPage();
        long pageNumber = search.getPageNumber();
        if (pageNumber > totalPage)
            pageNumber = totalPage;
        page.setCurrentPage(pageNumber);
        search.setPageNumber(pageNumber);
        resultMap.put(PubConst.PAGINATE_PAGE_KEY, page);
        resultMap.put(PubConst.PAGINATE_DATA_KEY, this.getList(search));
        return resultMap;
    }


    public T getDetailBySearch(TS search) {
        if (search == null) {
            try {
                search = getSearchClass().newInstance();
            } catch (Exception e) {
                return null;
            }
        }
        search.setLimit(1);
        try {
            return getList(search).get(0);
        } catch (Exception e) {
        }
        return null;
    }

}
