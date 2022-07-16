package ${pubPackage}.pubInter;


import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public interface IBaseFindMapper<T extends  BaseIdDoMain<KeyType>, TS extends BaseSearch, KeyType> {
    T getDetail(KeyType id);

    List<T> getList(@Param("search") TS search);

    int getListCount(@Param("search") TS search);

    /**
     * 返回全部数据
     *
     * @param search
     * @return
     */
    List<T> getAllList(@Param("search") TS search);
}
