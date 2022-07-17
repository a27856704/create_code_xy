package ${pubPackage}.pubInter;

import ${pubPackage}.pubInter.exception.SkException;

import java.util.List;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public interface IBaseFindDao<T extends  BaseIdDoMain<KeyType>, TS extends BaseSearch, KeyType> {

    T getDetail(KeyType id) throws SkException;

    List<T> getList(TS search) throws SkException;

    int getListCount(TS search) throws SkException;

    List<T> getAllList(TS search) throws SkException;
}
