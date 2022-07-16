package ${pubPackage}.pubInter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ${pubPackage}.common.BeanUtils;
import ${pubPackage}.pubInter.common.PageBean;
import ${pubPackage}.pubInter.common.PubConst;
import ${pubPackage}.pubInter.exception.SkException;
import ${pubPackage}.web.common.SkJsonResult;
import ${pubPackage}.pubInter.baseVO.AbstractDataVO;
import ${pubPackage}.pubInter.baseVO.AbstractPageVO;
import ${pubPackage}.pubInter.baseVO.DecorateModel;
import ${pubPackage}.pubInter.baseVO.DecoratePageList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;



/**
* @author ${author}
* @Date ${createTime}
* @description 接口需要的继承在类
*/
@Api("通用接口")
@RestController
public abstract class RestfulController<
        DTO extends AbstractDTO
        , PageDTO extends AbstractPageDTO
        , T extends BaseIdDoMain<KeyType>
        , TS extends BaseSearch
        , DomainVO extends AbstractDomainVO<KeyType>
        , DetailDomainVO extends DomainVO
        , DetailVO extends AbstractDataVO<DetailDomainVO>
        , ListVO extends AbstractPageVO<DetailDomainVO>,KeyType> extends BaseController {

      public abstract IBaseService<T, TS, KeyType> getBaseService() throws SkException;

    public abstract String getBaseRoute() throws SkException;

    public final Class<DTO> getDtoClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }

    public final Class<PageDTO> getPageDtoClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[1];
    }

    public final Class<T> getDomainClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[2];
    }

    public final Class<TS> getSearchClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[3];
    }

    public final Class<DomainVO> getDomainVOClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[4];
    }

    public final Class<DetailDomainVO> getDomainDetailVOClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[5];
    }


    public final Class<DetailVO> getDetailVOClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[6];
    }


    public final Class<ListVO> getListVOClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[7];
    }


     /**
     * 列表页面路由
     *
     * @return
     * @throws SkException
     */
    public String getListPageRoute() throws SkException {
        return getBaseRoute() + "list";
    }

    public String getAddPageRoute() throws SkException {
        return getBaseRoute() + "add";
    }

    public String getPostAddRoute() throws SkException {
        return getBaseRoute() + "postAdd";
    }

    public String getModPageRoute() throws SkException {
        return getBaseRoute() + "mod";
    }

    public String getPostModRoute() throws SkException {
        return getBaseRoute() + "postMod";
    }

    public String getPostDelRoute() throws SkException {
        return getBaseRoute() + "postDelete";
    }

    public String getPostDelAllRoute() throws SkException {
        return getBaseRoute() + "postDeleteAll";
    }

    public String getDetailPageRoute() throws SkException {
        return getBaseRoute() + "detail";
    }
    /**
    * 当前模板名称
    * @return
    */
    public String getModelName(){
        return "";
    }


    /**
     * 详情页面
     *
     * @param id
     * @param request
     * @return
     * @throws SkException
     */


    @ApiOperation(value = "详情页面", notes = "详情页面接口", httpMethod = "GET")
    @GetMapping({"detail/{id}"})
    @ResponseBody
    public SkJsonResult<DetailVO> getDetail(@ApiParam("id") @PathVariable KeyType id
            ,  HttpServletRequest request,HttpSession session) throws SkException {
        T domain = getBaseService().getDetail(id);
        SkJsonResult<DetailVO> jsonResult = SkJsonResult.ok();
        //jsonResult.setData(map(new DecorateModel<DetailDomainVO>(map(domain, getDomainDetailVOClass())), getDetailVOClass()));
        jsonResult.setData(map(new DecorateModel<T>(domain), getDetailVOClass()));
        detailExtend(domain, jsonResult.getData(), request, session);

        return jsonResult;

    }

    /**
     * 详情页面数据扩展
     *
     * @param t
     * @param detailVO
     * @param request
     */

    public void detailExtend(T t, DetailVO detailVO, HttpServletRequest request, HttpSession session) {


    }


    /**
     * 添加数据前检测
     *
     * @param dto
     * @param domain
     * @param session
     * @return
     * @throws SkException
     */
    public boolean beforePostAdd(DTO dto,T domain, HttpServletRequest request, HttpSession session) throws SkException {
        return true;
    }

    @ApiOperation(value = "通用添加接口", notes = "通用添加接口", httpMethod = "POST")
    @PostMapping("postAdd")
    @ResponseBody
    public SkJsonResult<DetailVO> postAdd(@ApiParam(required = true, name = "dto", value
            = "通用基础dto") @Valid @RequestBody DTO dto,  HttpServletRequest request,  HttpSession session) throws SkException {
        SkJsonResult<DetailVO> jsonResult = SkJsonResult.ok();
        if (dto == null) {
            return jsonResult;
        }
        T domain = map(dto, getDomainClass());
        if (!beforePostAdd(dto,domain, request, session)) {
            return SkJsonResult.fail("beforePostAdd false;");
        }
        getBaseService().insert(domain);
        jsonResult.setData(map(new DecorateModel<T>(domain), getDetailVOClass()));
        afterPostAdd(dto,domain, jsonResult.getData(), request, session);
        return jsonResult;
    }

    /**
     * 添加插入数据后的操作
     *
     * @param dto
     * @param domain
     * @param detailVO
     * @param request
     * @param session
     * @throws SkException
     */
    public void afterPostAdd(DTO dto,T domain, DetailVO detailVO, HttpServletRequest request, HttpSession session) throws SkException {

    }

    /**
     * 修改提交数据
     *
     * @param id
     * @param dto
     * @param session
     * @return
     * @throws SkException
     */
    @ApiOperation(value = "通用修改接口", notes = "通用修改接口", httpMethod = "POST")
    @PostMapping("postMod/{id}")
    @ResponseBody
    public SkJsonResult<DetailVO> postMod(@ApiParam("id") @PathVariable KeyType id, @Valid @RequestBody DTO dto,  HttpServletRequest request,  HttpSession session) throws SkException {

        SkJsonResult<DetailVO> jsonResult = SkJsonResult.ok();
        if (dto == null) {
            return jsonResult;
        }
        T newDomain = map(dto, getDomainClass());
        T oldDomain = getBaseService().getDetail(id);
        if (!beforePostMod(dto, newDomain,oldDomain,request, session)) {
            return SkJsonResult.fail("beforePostMod false;");
        }

        org.springframework.beans.BeanUtils.copyProperties(newDomain, oldDomain, postModNoUpdate(newDomain));
        getBaseService().update(oldDomain);
        jsonResult.setData(map(new DecorateModel<T>(oldDomain), getDetailVOClass()));
        afterPostMod(dto,oldDomain, jsonResult.getData(), request, session);
        return jsonResult;

    }


    /**
     * 删除单个
     *
     * @param id
     * @return
     * @throws SkException
     */
    @ApiOperation(value = "通用删除接口", notes = "通用删除接口", httpMethod = "POST")
    @PostMapping("postDelete/{id}")
    @ResponseBody
    public SkJsonResult<String> postDelete(@ApiParam("id") @PathVariable("id") KeyType id,  HttpServletRequest request,  HttpSession session) throws SkException {
        if (!beforePostDelete(id, request, session)) {
            return SkJsonResult.fail("beforePostDelete false");
        }
        getBaseService().delete(id);
        afterPostDelete(id, request, session);
        return SkJsonResult.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     * @throws
     */
    @ApiOperation(value = "通用批量删除接口", notes = "通用批量删除接口", httpMethod = "POST")
    @PostMapping("postDeleteAll")
    @ResponseBody
    public SkJsonResult<String> postDeleteAll(@ApiParam("主键IDs") @RequestParam(name = "ids[]") @RequestBody List<KeyType> ids,  HttpServletRequest request,  HttpSession session) throws SkException {

        if (ids == null || ids.size() == 0) {
            return SkJsonResult.fail("删除的条目为空");
        }
        if (beforePostDeleteAll(ids, request, session)) {
            return SkJsonResult.fail("beforePostDeleteAll false");
        }
        getBaseService().batchDelete(ids);
        afterPostDeleteAll(ids, request, session);
        return SkJsonResult.ok();
    }


    /**
     * 列表页面
     *
     * @param pageDTO
     * @param session
     * @return
     * @throws SkException
     */
    @ApiOperation(value = "通用列表接口", notes = "通用列表接口")
    @RequestMapping(value = "list", method = {RequestMethod.POST})
    @ResponseBody
    public SkJsonResult<ListVO> list(@ApiParam("搜索条件") @RequestBody PageDTO pageDTO,  HttpServletRequest request,  HttpSession session) throws SkException {
        TS search = pageDTOToSearch(pageDTO);
        searchConditionExtend(search, request, session);//附加搜索条件
        return listSkJsonResult(search, request, session);
    }

    public SkJsonResult<ListVO> listSkJsonResult(TS search, HttpServletRequest request, HttpSession session) throws SkException {
        SkJsonResult<ListVO> jsonResult = SkJsonResult.ok();
        fillListData(search, jsonResult, request, session);
        listPageExtend(search, jsonResult.getData(), request, session);
        return jsonResult;
    }


    public TS pageDTOToSearch(PageDTO pageDTO) {
        return map(pageDTO, getSearchClass());
    }







    /**
     * 排除不要修改的字段
     *
     * @param domain
     * @return
     */
    private String[] postModNoUpdate(T domain) {
        String[] excludeFiledArr = BeanUtils.getNullPropertyNames(domain);
        if (excludeFiledArr == null) {
            excludeFiledArr = new String[0];
        }
        String excludeFiledStr = addExcludeUpdateField(domain);
        if (excludeFiledStr == null || "".equals(excludeFiledStr)) {
            return excludeFiledArr;
        }
        String[] excludeFiled = excludeFiledStr.split(",");
        for (int i = 0; i < excludeFiled.length; i++) {
            excludeFiledArr = ArrayUtils.add(excludeFiledArr, excludeFiled[i]);
        }
        return excludeFiledArr;
    }

    /**
     * 添加额外不要修改的字段
     *
     * @param t
     * @return
     */
    public String addExcludeUpdateField(T t) {
        return "";
    }

    /**
     * 修改数据前
     *
     * @param dto
     * @param newDomain
     * @param oldDomain
     * @param session
     * @return
     * @throws SkException
     */
    public boolean beforePostMod(DTO dto,T newDomain, T,oldDomain,HttpServletRequest request, HttpSession session) throws SkException {
        return true;
    }

    /**
     * 修改插入数据后的操作
     *
     * @param dto
     * @param domain
     * @param detailVO
     * @param session
     * @throws SkException
     */
    public void afterPostMod(DTO dto,T domain, DetailVO detailVO, HttpServletRequest request, HttpSession session) throws SkException {

    }


    /**
     * 删除数据前
     *
     * @param id
     * @param session
     * @return
     * @throws SkException
     */
    public boolean beforePostDelete(KeyType id, HttpServletRequest request, HttpSession session) throws SkException {
        return true;
    }

    /**
     * 删除数据后的操作
     *
     * @param id
     * @param session
     * @throws SkException
     */
    public void afterPostDelete(KeyType id, HttpServletRequest request, HttpSession session) throws SkException {

    }


    /**
     * 删除数据前
     *
     * @param ids
     * @param session
     * @return
     * @throws SkException
     */
    public boolean beforePostDeleteAll(List<KeyType> ids, HttpServletRequest request, HttpSession session) throws SkException {
        return true;
    }

    /**
     * 删除数据后的操作
     *
     * @param ids
     * @param session
     * @throws SkException
     */
    public void afterPostDeleteAll(List<KeyType> ids, HttpServletRequest request, HttpSession session) throws SkException {

    }


    /**
     * 列表页面增加
     *
     * @param search
     * @param listVO
     * @param session
     * @throws SkException
     */
    public void listPageExtend(TS search, ListVO listVO, HttpServletRequest request, HttpSession session) throws SkException {


    }

    /**
     * 列表数据
     *
     * @param search
     * @param jsonResult
     * @param request
     * @param session
     * @throws SkException
     */
    public void fillListData(TS search, SkJsonResult<ListVO> jsonResult, HttpServletRequest request, HttpSession session) throws SkException {
        Map<String, Object> skMap = getBaseService().paginate(search);

        DecoratePageList pageList = new DecoratePageList(decorateList((List<T>) skMap.get(PubConst.PAGINATE_DATA_KEY)), (PageBean) skMap.get(PubConst.PAGINATE_PAGE_KEY));
        jsonResult.setData(map(pageList, getListVOClass()));
    }

    /**
     * 装饰list数据
     *
     * @param dataList
     * @return
     */
    public List<T> decorateList(List<T> dataList) {
        return dataList;
    }

    /**
     * 搜索条件附加
     *
     * @param search
     * @throws SkException
     */
    public void searchConditionExtend(TS search, HttpServletRequest request, HttpSession session) throws SkException {

    }
}