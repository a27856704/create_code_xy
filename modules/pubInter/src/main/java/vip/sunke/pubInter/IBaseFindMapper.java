package vip.sunke.pubInter;


import java.util.List;

/**
 * @author sunke
 * @Date 2017/6/12 12:01
 * @description
 */

public interface IBaseFindMapper<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> {
    T getDetail(KeyType id);

    List<T> getList(TS search);

    int getListCount(TS search);

    /**
     * 返回全部数据
     *
     * @param search
     * @return
     */
    List<T> getAllList(TS search);
}
