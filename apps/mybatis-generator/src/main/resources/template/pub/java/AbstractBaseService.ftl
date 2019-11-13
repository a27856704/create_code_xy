package ${pubPackage}.pubInter;


import ${pubPackage}.pubInter.exception.DaoException;
import ${pubPackage}.pubInter.exception.DaoExceptionEnum;
import ${pubPackage}.pubInter.exception.SkException;

import java.util.List;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public abstract class AbstractBaseService<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> extends AbstractBaseFindService<T, TS, KeyType> {

    public abstract IBaseDao<T, TS, KeyType> getDao();

    public T insert(T obj) throws SkException {
        int result = getDao().insert(obj);
        if (result == 0)
            throw new DaoException(DaoExceptionEnum.DAO_INSERT_ERROR);
        return obj;
    }

    public int delete(KeyType id) throws SkException {
        int result = getDao().delete(id);
        if (result == 0)
            throw new DaoException(DaoExceptionEnum.DAO_DEL_ERROR);
        return result;
    }

    public int batchDelete(List<KeyType> list) throws SkException {
        int result = getDao().batchDelete(list);
        if (result == 0)
            throw new DaoException(DaoExceptionEnum.DAO_DEL_ERROR);
        return result;
    }

    public T update(T obj) throws SkException {
        int result = getDao().update(obj);
        if (result == 0)
            throw new DaoException(DaoExceptionEnum.DAO_UPDATE_ERROR);
        return obj;
    }


    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    public List<T> batchInsert(List<T> list) throws SkException {
        if (list == null || list.size() == 0)
            return list;
        getDao().batchInsert(list);
        return list;
    }
}
