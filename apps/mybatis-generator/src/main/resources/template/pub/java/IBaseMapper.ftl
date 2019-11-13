package ${pubPackage}.pubInter;


import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public interface IBaseMapper<T extends AbstractBaseDoMain, TS extends BaseSearch, KeyType> extends SearchMapper<T, TS, KeyType> {

    /**
     * 添加
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 更新
     *
     * @param t
     * @return
     */

    int update(T t);


    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(KeyType id);


    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<T> list);

    /**
     * 批量删除
     *
     * @param list
     * @return
     */
    int batchDelete(@Param("list") List<KeyType> list);


}
