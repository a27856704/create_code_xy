package vip.sunke.pubInter;

import org.apache.ibatis.annotations.Param;
import vip.sunke.pubInter.exception.DaoException;
import vip.sunke.pubInter.exception.DaoExceptionEnum;
import vip.sunke.pubInter.exception.SkException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author sunke
 * @Date 2019-09-05 12:28:02
 * @description
 */

public abstract class AbstractBaseDao<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> extends AbstractBaseFindDao<T, TS, KeyType> {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public abstract IBaseMapper<T, TS, KeyType> getMapper();

    public int insert(T t) throws SkException {
        if (t == null)
            return 0;
        setIdAndTime(t);
        try {
            return getMapper().insert(t);
        } catch (Exception e) {
            throw new DaoException(DaoExceptionEnum.DAO_INSERT_ERROR);
        }
    }

    public int delete(KeyType id) throws SkException {
        try {
            return getMapper().delete(id);
        } catch (Exception e) {
            throw new DaoException(DaoExceptionEnum.DAO_DEL_ERROR);
        }
    }

    public int batchDelete(@Param("list") List<KeyType> list) throws SkException {
        if (list == null || list.size() == 0)
            return 0;
        try {
            return getMapper().batchDelete(list);
        } catch (Exception e) {
            throw new DaoException(DaoExceptionEnum.DAO_DEL_ERROR);
        }
    }

    public int update(T t) throws SkException {
        if (t == null)
            return 0;
        if (t instanceof BaseTimeDoMain) {
            ((BaseTimeDoMain) t).setUpdateTime(new Date());
        }
        try {
            return getMapper().update(t);
        } catch (Exception e) {
            throw new DaoException(DaoExceptionEnum.DAO_UPDATE_ERROR);
        }
    }

    /**
     * 批量更新
     *
     * @param t
     * @return
     */
    public int batchUpdate(List<KeyType> strings, T t) throws SkException {
        if (t == null)
            return 0;
        try {
            return getMapper().batchUpdate(strings, t);
        } catch (Exception e) {
            throw new DaoException(DaoExceptionEnum.DAO_UPDATE_ERROR);
        }
    }

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    public int batchInsert(List<T> list) throws SkException {
        if (list == null || list.size() == 0)
            return 0;
        for (T t : list) {
            setIdAndTime(t);
        }
        try {
            return getMapper().batchInsert(list);
        } catch (Exception e) {
            throw new DaoException(DaoExceptionEnum.DAO_INSERT_ERROR);
        }
    }

    /**
     * 设置主键和创建时间和更新时间
     *
     * @param t
     */
    private void setIdAndTime(T t) {
        if (t instanceof BaseIdDoMain) {
            if (((BaseIdDoMain) t).getId() == null) {
                try {
                    if ("String".equals(t.getClass().getMethod("getId").getReturnType().getSimpleName())) {
                        ((BaseTimeDoMain) t).setId(AbstractBaseDao.uuid());
                    }
                } catch (NoSuchMethodException e) {
                }
            }
        }
        if (t instanceof BaseTimeDoMain) {
            Date current = new Date();
            if (((BaseTimeDoMain) t).getCreateTime() == null) {
                ((BaseTimeDoMain) t).setCreateTime(current);
            }
            if (((BaseTimeDoMain) t).getUpdateTime() == null) {
                ((BaseTimeDoMain) t).setUpdateTime(current);
            }
        }
    }
}
