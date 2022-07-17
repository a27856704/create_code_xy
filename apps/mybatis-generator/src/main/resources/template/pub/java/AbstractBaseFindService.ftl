package ${pubPackage}.pubInter;

import ${pubPackage}.common.SkList;
import ${pubPackage}.pubInter.baseVO.DecoratePageList;
import ${pubPackage}.pubInter.baseVO.DecorateModel;
import ${pubPackage}.pubInter.common.PageBean;
import ${pubPackage}.pubInter.common.PubConst;
import ${pubPackage}.pubInter.exception.BusinessException;
import ${pubPackage}.pubInter.exception.DaoException;
import ${pubPackage}.pubInter.exception.DaoExceptionEnum;
import ${pubPackage}.pubInter.exception.SkException;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public abstract class AbstractBaseFindService<T extends BaseIdDoMain<KeyType>, TS extends BaseSearch, KeyType> {


    public abstract IBaseFindDao<T, TS, KeyType> getDao();


    /**
     * 根据某一个字段In 查询
     *
     * @param inValue
     * @param field
     * @param fullConfigSet
     * @return
     * @throws SkException
     */
    public List<T> getAllByInField(List inValue, String field, Set<String> fullConfigSet) throws SkException {

        if (StringUtil.isNullOrEmpty(inValue)) {
            return null;
        }
        inValue = (List) (inValue.stream().filter(item -> StringUtil.isNotObjEmpty(item)).collect(Collectors.toList()));
        if (StringUtil.isNullOrEmpty(inValue)) {
            return null;
        }
        try {
            TS search = getSearchClass().newInstance();
            search.setFullConfigSet(fullConfigSet);
            search.setInField(field, inValue);
            return getAllList(search);

        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }

        return null;

    }


    public List<T> getAllByIds(List ids, Set<String> fullConfigSet) throws SkException {

        if (StringUtil.isNullOrEmpty(ids)) {
            return null;
        }
        ids = (List) ids.stream().filter(id -> StringUtil.isNotObjEmpty(id)).collect(Collectors.toList());
        if (StringUtil.isNullOrEmpty(ids)) {
            return null;
        }
        try {
            TS search = getSearchClass().newInstance();
            search.setFullConfigSet(fullConfigSet);
            search.setInField(search.pkField(), ids);
            return getAllList(search);

        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }

        return null;


    }


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

        Set<String> setConfig = new HashSet<>();
        setConfig.add(FullConfigKeyConst.FULL_ALL_KEY);
        return getDetail(id, setConfig);
    }

    public T getDetail(KeyType id, Set<String> fullConfigSet) throws SkException {

        T result = getDao().getDetail(id);
        if (result == null) {
            throw new DaoException(DaoExceptionEnum.DAO_GET_ERROR);
        }

        setOtherToDetail(result, fullConfigSet);

        return result;
    }

    public T getDetailByUnFullConfigSet(KeyType id, Set<String> unFullConfigSet) throws SkException {

        T result = getDao().getDetail(id);
        if (result == null) {
            throw new DaoException(DaoExceptionEnum.DAO_GET_ERROR);
        }

        setOtherToDetail(result, FullConfigKeyConst.excludeFullData(unFullConfigSet));

        return result;
    }



  /*  public void fullOtherData(List<T> list) {

        fullOtherData(list, null);

    }*/


    public void fullOtherData(List<T> list, Set<String> fullConfigSet) {


    }

    /**
     * 列表时附加数据
     *
     * @param list
     * @throws SkException
     */
    protected void setOtherToList(List<T> list, Set<String> fullConfigSet) throws SkException {

        fullOtherData(list, fullConfigSet);

    }


    /**
     * 详情时附加数据
     *
     * @param t
     * @throws SkException
     */
    protected void setOtherToDetail(T t, Set<String> fullConfigSet) throws SkException {

        fullOtherData(SkList.getInstance().addObjToList(t), fullConfigSet);

    }


    public List<T> getList(TS search) throws SkException {
        List<T> list = null;
        if (search != null) {
            list = getDao().getList(search);

            /*if(StringUtil.isNullOrEmpty(search.getFullConfigSet())){
                Set<String> setConfig=new HashSet<>();
                setConfig.add(Const.FULL_ALL_KEY);
                setOtherToList(list, setConfig);
            }else {*/

            //说明有要排除的，就优先排除
            if (StringUtil.isNotObjEmpty(search.getUnFullConfigSet())) {
                search.setFullConfigSet(FullConfigKeyConst.excludeFullData(search.getUnFullConfigSet()));
            }
            setOtherToList(list, search.getFullConfigSet());
            return list;

        }

        try {
            list = getDao().getList(getSearchClass().newInstance());

           /* //说明有要排除的，就优先排除
            if (StringUtil.isNotObjEmpty(search.getUnFullConfigSet())) {
                search.setFullConfigSet(FullConfigKeyConst.excludeFullData(search.getUnFullConfigSet()));
            }*/

            setOtherToList(list, null);
            // }
            return list;
        } catch (Exception e) {
            return null;
        }
    }


    public int getListCount(TS search) throws SkException {
        if (search == null) {
            try {
                return getDao().getListCount(getSearchClass().newInstance());
            } catch (Exception e) {

                return 0;
            }
        }
        return getDao().getListCount(search);
    }

    public List<T> getAllList(TS search) throws SkException {

        List<T> list = null;
        if (search != null) {
            list = getDao().getAllList(search);
            //说明有要排除的，就优先排除
            if (StringUtil.isNotObjEmpty(search.getUnFullConfigSet())) {
                search.setFullConfigSet(FullConfigKeyConst.excludeFullData(search.getUnFullConfigSet()));
            }
            setOtherToList(list, search.getFullConfigSet());
            return list;

        }

        try {
            list = getDao().getAllList(getSearchClass().newInstance());

            setOtherToList(list, null);
            // }
            return list;
        } catch (Exception e) {
            return null;
        }


    }

    public DecoratePageList<T> paginateToObject(TS search) throws SkException {
        Map skMap = paginate(search);
        return new DecoratePageList(((List<T>) skMap.get(PubConst.PAGINATE_DATA_KEY)), (PageBean) skMap.get(PubConst.PAGINATE_PAGE_KEY));
    }

    public DecorateModel<T> getDetailToObject(KeyType id) throws SkException {
        return getDetailToObject(id, null);
    }

    public DecorateModel<T> getDetailToObject(KeyType id, Set<String> fullConfigSet) throws SkException {
        return new DecorateModel<T>(getDetail(id, fullConfigSet));
    }

    public DecorateModel<T> getDetailToObjectByUnFullConfigSet(KeyType id, Set<String> unFullConfigSet) throws SkException {
        return new DecorateModel<T>(getDetailByUnFullConfigSet(id, unFullConfigSet));
    }

    /**
     * 分頁
     *
     * @param search
     * @return
     * @throws SkException key = "'86400#'+#root.targetClass+'.page.'+#search.toString()"
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
        page.setTotalCount(getDao().getListCount(search));
        page.setPageSize(search.getLimit());

        //说明显示全部
        if (search.getLimit() == -1) {
            page.setPageSize(page.getTotalCount());
            search.setPageSize(page.getPageSize());
        }

        long totalPage = page.getTotalPage();
        long pageNumber = search.getPageNumber();
        if (pageNumber > totalPage) {
            pageNumber = totalPage;
        }
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

            T obj = getList(search).get(0);

            //说明有要排除的，就优先排除
            if (StringUtil.isNotObjEmpty(search.getUnFullConfigSet())) {
                search.setFullConfigSet(FullConfigKeyConst.excludeFullData(search.getUnFullConfigSet()));
            }
            setOtherToDetail(obj, search.getFullConfigSet());

            /*if (StringUtil.isNullOrEmpty(search.getFullConfigSet())) {
                Set<String> setConfig = new HashSet<>();
                setConfig.add(Const.FULL_ALL_KEY);
                setOtherToDetail(obj, setConfig);
            } else {
                setOtherToDetail(obj, search.getFullConfigSet());

            }
*/

            return obj;
        } catch (Exception e) {

        }
        return null;
    }

    public int getCountBySearch(TS search) {
        try {
            if (search == null) {
                return 0;
            }
            return getListCount(search);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }
}
