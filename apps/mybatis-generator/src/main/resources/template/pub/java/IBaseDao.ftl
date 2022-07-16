package ${pubPackage}.pubInter;


import ${pubPackage}.pubInter.exception.SkException;

import java.util.List;
import java.util.Map;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public interface IBaseDao<T extends  BaseIdDoMain<KeyType>, TS extends BaseSearch, KeyType> extends IBaseFindDao<T, TS, KeyType> {

    int insert(T t) throws SkException;

    int delete(KeyType id) throws SkException;

    int update(T t) throws SkException;

    /**
     * 批量删除
     *
     * @param list
     * @return
     * @throws SkException
     */
    int batchDelete(List<KeyType> list) throws SkException;

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int batchInsert(List<T> list) throws SkException;


    /**
    * 按map里的数据更新
    * @param dataMap ，key 是字段名 value 要更新的数据
    * @return
    */
    boolean updateDataBySearch(Map<String,Object>dataMap,TS search ) throws SkException;

    /**
    * 按map里的数据更新
    * @param dataMap ，key 是字段名 value 要更新的数据
    * @return
    */
    boolean updateDataById(Map<String,Object>dataMap,KeyType id) throws SkException;



}