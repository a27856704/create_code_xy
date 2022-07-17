package ${pubPackage}.pubInter;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public interface IBaseMapper<T extends BaseIdDoMain<KeyType>, TS extends BaseSearch, KeyType> extends SearchMapper<T, TS, KeyType> {

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

    /**
    * 按map里的数据更新
    * @param dataMap ，key 是字段名 value 要更新的数据
    * @return
    */
    int updateDataBySearch(@Param("dataMap") Map<String,Object>dataMap,@Param("search") TS search );

    /**
    * 按map里的数据更新
    * @param dataMap ，key 是字段名 value 要更新的数据
    * @return
    */
    int updateDataById(@Param("dataMap") Map<String,Object>dataMap,@Param("id") KeyType id );




}
