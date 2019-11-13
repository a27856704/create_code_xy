package vip.sunke.pubInter;


import vip.sunke.pubInter.exception.SkException;

import java.util.List;

/**
 * @author sunke
 * @Date 2017/6/9 14:21
 * @description
 */

public interface IBaseService<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> extends IBaseFindService<T, TS, KeyType> {


    T insert(T obj) throws SkException;

    int delete(KeyType id) throws SkException;

    T update(T t) throws SkException;

    int batchDelete(List<KeyType> list) throws SkException;

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    List<T> batchInsert(List<T> list) throws SkException;



    /**
     * 批量更新
     *
     * @param t
     * @return
     */
    int batchUpdate(List<KeyType> strings, T t) throws SkException;


}
