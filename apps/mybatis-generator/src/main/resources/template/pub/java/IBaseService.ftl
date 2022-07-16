package ${pubPackage}.pubInter;


import ${pubPackage}.pubInter.exception.SkException;

import java.util.List;
import java.util.Map;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public interface IBaseService<T extends BaseIdDoMain<KeyType>, TS extends BaseSearch, KeyType> extends IBaseFindService<T, TS, KeyType> {

    /**
        * 添加
        *
        * @param obj
        * @return
        * @throws SkException
    */
    T insert(T obj) throws SkException;

    /**
        * 删除
        *
        * @param id
        * @return
        * @throws SkException
    */
    int delete(KeyType id) throws SkException;

    /**
        * 删除
        *
        * @param obj
        * @return
        * @throws SkException
    */
    int delete(T obj) throws SkException;

    /**
        * 更新
        *
        * @param t
        * @return
        * @throws SkException
    */

    T update(T t) throws SkException;

    /**
        * 批量删除
        *
        * @param list
        * @return
        * @throws SkException
    */

    int batchDelete(List<KeyType> list) throws SkException;

    /**
        * 批量删除
        *
        * @param list
        * @return
        * @throws SkException
    */
    int batchDeleteByList(List<T> list) throws SkException;

    /**
        * 删除
        *
        * @param list
        * @return
        * @throws SkException
    */
    List<T> batchInsert(List<T> list) throws SkException;

    /**
        * 删除
        *
        * @param ts
        * @return
        * @throws SkException
    */
    int batchDeleteBySearch(TS ts) throws SkException;

    /**
    * 按map里的数据更新
    * @param dataMap ，key 是字段名 value 要更新的数据
    * @return
    */
    boolean updateDataBySearch( Map<String,Object>dataMap, TS search ) throws SkException;

    /**
    * 按map里的数据更新
    * @param dataMap ，key 是字段名 value 要更新的数据
    * @return
    */
    boolean updateDataById( Map<String,Object>dataMap, KeyType id ) throws SkException;

    /**
     * 逻辑删除
     *
     * @param search
     * @return
     * @throws SkException
     */
    int delLogicBySearch(TS search) throws SkException;

    /**
     * 逻辑删除
     * @param id
     * @return
     * @throws SkException
     */
    int delLogicById(KeyType id) throws SkException;

}
