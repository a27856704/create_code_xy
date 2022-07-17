package ${pubPackage}.pubInter;

import ${pubPackage}.common.StringUtil;
import ${pubPackage}.pubInter.exception.DaoException;
import ${pubPackage}.pubInter.exception.DaoExceptionEnum;
import ${pubPackage}.pubInter.exception.SkException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Date;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/

public abstract class AbstractBaseService<T extends BaseIdDoMain<KeyType>, TS extends BaseSearch, KeyType> extends AbstractBaseFindService<T, TS, KeyType> {

    @Override
    public abstract IBaseDao<T, TS, KeyType> getDao();

    public T insert(T obj) throws SkException {
        int result = getDao().insert(obj);
        if (result == 0){
            throw new DaoException(DaoExceptionEnum.DAO_INSERT_ERROR);
        }
        return obj;
    }

    public int delete(KeyType id) throws SkException {
        int result = getDao().delete(id);
        if (result == 0){
            throw new DaoException(DaoExceptionEnum.DAO_DEL_ERROR);
        }
        return result;
    }

    public int delete(T obj) throws SkException {
        if (obj == null) {
            throw new DaoException(DaoExceptionEnum.DAO_GET_ERROR);
        }
        int result = getDao().delete(obj.getId());
        if (result == 0) {
            throw new DaoException(DaoExceptionEnum.DAO_DEL_ERROR);
        }
        return result;
    }


    public int batchDelete(List<KeyType> list) throws SkException {
        int result = getDao().batchDelete(list);
        if (result == 0){
            throw new DaoException(DaoExceptionEnum.DAO_DEL_ERROR);
        }
        return result;
    }

    public int batchDeleteByList(List<T> list) throws SkException {

        if (StringUtil.isObjEmpty(list)){
            return 0;
        }


        int result = getDao().batchDelete(list.stream().map(t -> {
            if (t instanceof BaseIdDoMain) {
                return ((BaseIdDoMain<KeyType>) t).getId();
            }
                return null;
        }).collect(Collectors.toList()));

        if (result == 0){
            throw new DaoException(DaoExceptionEnum.DAO_DEL_ERROR);
        }
        return result;
    }





    public T update(T obj) throws SkException {
        int result = getDao().update(obj);
        if (result == 0){
            throw new DaoException(DaoExceptionEnum.DAO_UPDATE_ERROR);
        }
        return obj;
    }


    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    public List<T> batchInsert(List<T> list) throws SkException {
        if (list == null || list.size() == 0){
            return list;
        }
        getDao().batchInsert(list);
        return list;
    }


    public int batchDeleteBySearch(TS ts) throws SkException {
        return batchDeleteByList(getAllList(ts));
    }

    /**
    * 按map里的数据更新
    * @param dataMap ，key 是字段名 value 要更新的数据
    * @return
    */
    public boolean updateDataBySearch(Map<String,Object>dataMap,TS search) throws SkException{
        try{
            if(search==null){
                 search= getSearchClass().newInstance();
             }

            if(search.updateTimeFiled()!=null && !"".equals(search.updateTimeFiled())){
                dataMap.put(search.updateTimeFiled(),new Date());
            }

            return getDao().updateDataBySearch(dataMap,search);
        }catch(Exception e){
            e.printStackTrace();
            throw new DaoException(DaoExceptionEnum.DAO_UPDATE_ERROR);

        }
    }

    /**
    * 按map里的数据更新
    * @param dataMap ，key 是字段名 value 要更新的数据
    * @return
    */
    public boolean updateDataById(Map<String,Object>dataMap,KeyType id) throws SkException{
        try{

            try{
                TS search= getSearchClass().newInstance();
                if(search.updateTimeFiled()!=null && !"".equals(search.updateTimeFiled())){
                    dataMap.put(search.updateTimeFiled(),new Date());
                }
            }catch(Exception e){

            }

            return getDao().updateDataById(dataMap,id);
        }catch(Exception e){
            e.printStackTrace();
            throw new DaoException(DaoExceptionEnum.DAO_UPDATE_ERROR);

        }

    }

        /**
     * 逻辑删除
     *
     * @param ts
     * @return
     * @throws SkException
     */
    public int delLogicBySearch(TS ts) throws SkException {
        try {
            Class<T> tClass = getDomainClass();
            String delField = (String) tClass.getField("DEL_FLAG").get("DEL_FLAG");
            return updateDataBySearch(SkMap.getInstance().addObjToMap(delField, WhetherEnum.YES.getValue()), ts) ? 1 : 0;
        } catch (Exception e) {

        }
        return 0;
    }


    /**
     * 逻辑删除
     *
     * @param id
     * @return
     * @throws SkException
     */
    public int delLogicById(KeyType id) throws SkException {

        if (StringUtil.isNullOrEmpty(id)) {
            return 0;
        }
        try {
            Class<T> tClass = getDomainClass();
            String delField = (String) tClass.getField("DEL_FLAG").get("DEL_FLAG");
            return updateDataById(SkMap.getInstance().addObjToMap(delField, WhetherEnum.YES.getValue()), id) ? 1 : 0;
        } catch (Exception e) {

        }
        return 0;
    }






}
