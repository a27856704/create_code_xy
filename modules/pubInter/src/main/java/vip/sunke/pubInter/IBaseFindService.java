package vip.sunke.pubInter;


import vip.sunke.pubInter.exception.SkException;

import java.util.List;
import java.util.Map;

/**
 * @author sunke
 * @Date 2017/10/11 11:10
 * @description
 */

public interface IBaseFindService<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> {

    T getDetail(KeyType id) throws SkException;

    List<T> getList(TS search) throws SkException;

    int getListCount(TS search) throws SkException;

    List<T> getAllList(TS search) throws SkException;

    Map<String, Object> paginate(TS search) throws SkException;

    T getDetailBySearch(TS search);

}
