package ${pubPackage}.pubInter;


import ${pubPackage}.pubInter.exception.SkException;

import ${pubPackage}.pubInter.baseVO.DecorateModel;
import ${pubPackage}.pubInter.baseVO.DecoratePageList;


import java.util.List;
import java.util.Map;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public interface IBaseFindService<T extends  BaseIdDoMain<KeyType>, TS extends BaseSearch, KeyType> {
    /**
     * 详情
     *
     * @param id pk
     * @return
     * @throws SkException
     */
    T getDetail(KeyType id) throws SkException;


    /**
     * 详情
     *
     * @param id            pk
     * @param fullConfigSet
     * @return
     * @throws SkException
     */
    T getDetail(KeyType id, Set<String> fullConfigSet) throws SkException;

    /**
     * @param id
     * @return
     * @throws SkException
     */
    DecorateModel<T> getDetailToObject(KeyType id) throws SkException;


    /**
     * @param id
     * @return
     * @throws SkException
     */
    DecorateModel<T> getDetailToObject(KeyType id, Set<String> fullConfigSet) throws SkException;

    /**
     * @param search
     * @return
     * @throws SkException
     */
    List<T> getList(TS search) throws SkException;

    /**
     * @param search
     * @return
     * @throws SkException
     */
    int getListCount(TS search) throws SkException;

    /**
     * @param search
     * @return
     * @throws SkException
     */
    List<T> getAllList(TS search) throws SkException;

    /**
     * @param search
     * @return
     * @throws SkException
     */
    Map<String, Object> paginate(TS search) throws SkException;

    DecoratePageList<T> paginateToObject(TS search) throws SkException;

    /**
     * @param search
     * @return
     */
    T getDetailBySearch(TS search);


    /**
     * @param search
     * @return
     */
    int getCountBySearch(TS search);


    /**
     * 根据IDs 得到所有
     *
     * @param ids
     * @param fullConfigSet
     * @return
     * @throws SkException
     */
    List<T> getAllByIds(List ids, Set<String> fullConfigSet) throws SkException;

    /**
     * 根据某一个字段In 查询
     *
     * @param inValue
     * @param field
     * @param fullConfigSet
     * @return
     * @throws SkException
     */
    List<T> getAllByInField(List inValue, String field, Set<String> fullConfigSet) throws SkException;

}
