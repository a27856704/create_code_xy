package vip.sunke.pubInter;

import vip.sunke.pubInter.exception.SkException;

import java.util.List;

/**
 * @author sunke
 * @Date 2017/10/11 11:08
 * @description
 */

public interface IBaseFindDao<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> {

    T getDetail(KeyType id) throws SkException;

    List<T> getList(TS search) throws SkException;

    int getListCount(TS search) throws SkException;

    List<T> getAllList(TS search) throws SkException;
}
