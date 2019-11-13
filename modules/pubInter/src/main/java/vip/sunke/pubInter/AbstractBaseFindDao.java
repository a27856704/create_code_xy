package vip.sunke.pubInter;

import vip.sunke.pubInter.exception.DaoException;
import vip.sunke.pubInter.exception.DaoExceptionEnum;
import vip.sunke.pubInter.exception.SkException;

import java.util.List;

/**
 * @author sunke
 * @Date 2017/10/11 11:12
 * @description
 */

public abstract class AbstractBaseFindDao<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> {
    public abstract IBaseFindMapper<T, TS, KeyType> getMapper();

    public T getDetail(KeyType id) throws SkException {
        try {
            return getMapper().getDetail(id);
        } catch (Exception e) {
            throw new DaoException(DaoExceptionEnum.DAO_DETAIL_ERROR);
        }
    }

    public List<T> getList(TS search) throws SkException {
        try {
            List<T> ts = getMapper().getList(search);
            return ts;
        } catch (Exception e) {
            throw new DaoException(DaoExceptionEnum.DAO_LIST_ERROR);
        }
    }

    public int getListCount(TS search) throws SkException {
        try {
            return getMapper().getListCount(search);
        } catch (Exception e) {
            throw new DaoException(DaoExceptionEnum.DAO_LIST_COUNT_ERROR);
        }
    }

    public List<T> getAllList(TS search) throws SkException {
        try {
            return getMapper().getAllList(search);
        } catch (Exception e) {
            throw new DaoException(DaoExceptionEnum.DAO_LIST_ERROR);
        }
    }

}
