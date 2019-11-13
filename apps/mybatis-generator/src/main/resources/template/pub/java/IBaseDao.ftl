package ${pubPackage}.pubInter;


import ${pubPackage}.pubInter.exception.SkException;

import java.util.List;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public interface IBaseDao<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> extends IBaseFindDao<T, TS, KeyType> {

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


}