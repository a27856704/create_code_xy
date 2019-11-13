package ${pubPackage}.pubInter;

import ${pubPackage}.common.BeanUtils;
import ${pubPackage}.common.Const;
import ${pubPackage}.pubInter.common.PubConst;
import ${pubPackage}.pubInter.exception.BusinessException;
import ${pubPackage}.pubInter.exception.BusinessExceptionEnum;
import ${pubPackage}.pubInter.exception.SkException;
import ${pubPackage}.web.common.SkMap;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
* @author ${author}
* @Date ${createTime}
* @description 完成基本增删改详情列表等操作
*/
@Validated
public abstract class ApiController<DTO extends AbstractDTO, T extends BaseIdDoMain, TS extends BaseSearch, KeyType, VO extends AbstractVO<KeyType>> extends BaseController implements IShowMenu {


    public Class<T> getDomainClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[1];
    }

    public Class<VO> getVOClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[4];
    }


    public Class getDetailVOClass() {
        return getVOClass();
    }


    @Override
    public String getCurrentMenu() {
        return null;
    }

    @Override
    public String getMenuModel() {
        return null;
    }


    @Override
    public String getAddTitle() {
        return "添加";
    }

    @Override
    public String getModTitle() {
        return "修改";
    }


    public IBaseService<T, TS, KeyType> getBaseService() throws SkException {
        throw new BusinessException(BusinessExceptionEnum.OBJ_NULL_ERROR);
    }

    public abstract String getBaseView();

    public abstract String getBaseRoute();

    /**
     * 添加时额外数据组装
     *
     * @param t
     */
    public void addExtra(T t, HttpServletRequest request) throws SkException {

        if (t instanceof BaseIdDoMain) {
            if (((BaseIdDoMain) t).getId() == null) {
                try {
                    //if ("String".equals(t.getKeyTypeClassName())) {
                        ((BaseIdDoMain) t).setId(AbstractBaseDao.uuid());
                  // }
                } catch (Exception e) {
                }
            }
        }


    }

    /**
     * 修改时额外数据组装
     *
     * @param t
     */
    public void modExtra(T t, HttpServletRequest request) throws SkException {


    }

    /**
     * 修改时额外操作
     *
     * @param id
     */
    public void deleteExtra(KeyType id, HttpServletRequest request) throws SkException {

    }

    public void searchExtra(TS ts, SkMap resultMap, HttpServletRequest request) throws SkException {
        pageExtra(resultMap, request);
    }

    /**
     * 添加页面
     *
     * @param resultMap
     * @throws BusinessException
     */
    public void addPageExtra(T t, SkMap resultMap, HttpServletRequest request) throws SkException {
        pageExtra(resultMap, request);
    }

    public void pageExtra(SkMap resultMap, HttpServletRequest request) {
        resultMap.set("currentMenu", getCurrentMenu());
        resultMap.set("menuModel", getMenuModel());
    }

    /**
     * 添加页面的数据填充
     *
     * @param dto
     * @return
     * @throws BusinessException
     */
    @GetMapping(value = "add")
    @ApiOperation(value = "通用添加页面数据渲染接口", notes = "通用添加页面数据渲染接口", httpMethod = "GET")
    @ResponseBody
    public SkMap addPage(@ApiParam(required = true, name = "dto", value
            = "通用基础dto") DTO dto, HttpServletRequest request) throws SkException {

        SkMap resultMap = SkMap.ok();
        T t = map(dto, getDomainClass());

        addPageExtra(t, resultMap, request);
        resultMap.set("domain", map(t, getDetailVOClass()));

        return resultMap;
    }

    /**
     * 添加提交
     *
     * @param dto
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "add")
    @ApiOperation(value = "通用提交添加接口", notes = "通用提交添加接口", httpMethod = "POST")
    @ResponseBody
    public SkMap add(@ApiParam(required = true, name = "dto", value
            = "通用基础dto") @Valid DTO dto, HttpServletRequest request) throws SkException {
        if (dto == null)
            return SkMap.ok();
        T t = map(dto, getDomainClass());
        addExtra(t, request);
        getBaseService().insert(t);
        return SkMap.ok();
    }

    /**
     * 修改页面附加内容
     *
     * @param resultMap
     * @throws BusinessException
     */
    public void modPageExtra(T t, SkMap resultMap, HttpServletRequest request) throws SkException {
        pageExtra(resultMap, request);
    }

    /**
     * 修改页面
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @GetMapping(value = "mod/{id}")
    @ApiOperation(value = "通用修改页面数据渲染接口", notes = "通用修改页面数据渲染接口", httpMethod = "GET")
    @ResponseBody
    public SkMap mod(@ApiParam(name = "id", value = "需要修改列的id", required = true) @PathVariable KeyType id, HttpServletRequest request) throws SkException {
        T t = getBaseService().getDetail(id);
        SkMap resultMap = SkMap.ok();
        resultMap.set("domain", map(t, getDetailVOClass()));
        modPageExtra(t, resultMap, request);
        return resultMap;
    }


    /**
     * 详情页面
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @GetMapping(value = "detailToJson/{id}")
    @ApiOperation(value = "通用详情页面数据渲染接口", notes = "通用详情页面数据渲染接口", httpMethod = "GET")
    @ResponseBody
    public SkMap detailToJson(@ApiParam(name = "id", value = "需要查看列的id", required = true) @PathVariable KeyType id, HttpServletRequest request) throws SkException {
        T t = getBaseService().getDetail(id);
        SkMap resultMap = SkMap.ok();
        resultMap.set("domain", map(t, getDetailVOClass()));
        resultMap.set("modPage", getBaseRoute() + "mod/" + id);
        detailPageExtra(t, resultMap, request);
        return resultMap;
    }


    /**
     * 详情页面附加内容
     *
     * @param resultMap
     * @throws BusinessException
     */
    public void detailPageExtra(T t, SkMap resultMap, HttpServletRequest request) throws SkException {
    }

    /**
     * 修改提交
     *
     * @param id
     * @param dto
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "mod/{id}")
    @ApiOperation(value = "通用提交修改接口", notes = "通用提交修改接口", httpMethod = "POST")
    @ResponseBody
    public SkMap mod(@ApiParam(name = "id", value = "需要修改列的id", required = true) @PathVariable KeyType id,
                     @ApiParam(required = true, name = "dto", value = "通用基础dto") @Valid DTO dto, HttpServletRequest request) throws SkException {
        if (dto == null)
            return SkMap.ok();
        T t = map(dto, getDomainClass());
        modExtra(t, request);
        T detail = getBaseService().getDetail(id);
        org.springframework.beans.BeanUtils.copyProperties(t, detail, postModNoUpdate(t));
        // BeanUtils.copyPropertiesIgnoreNull(t, detail);
        getBaseService().update(detail);
        return SkMap.ok();
    }

    /**
     * 排除不要修改的字段
     *
     * @param t
     * @return
     */
    public String[] postModNoUpdate(T t) {
        String[] excludeFiledArr = BeanUtils.getNullPropertyNames(t);
        if (excludeFiledArr == null)
            excludeFiledArr = new String[0];
        String excludeFiledStr = addExcludeUpdateField(t);
        if (excludeFiledStr == null || "".equals(excludeFiledStr))
            return excludeFiledArr;
        String[] excludeFiled = excludeFiledStr.split(",");
        for (int i = 0; i < excludeFiled.length; i++) {
            excludeFiledArr = ArrayUtils.add(excludeFiledArr, excludeFiled[i]);
        }
        return excludeFiledArr;
    }

    public String addExcludeUpdateField(T t) {
        return "";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "delete")
    @ApiOperation(value = "通用提交删除接口", notes = "通用提交删除接口", httpMethod = "POST")
    @ResponseBody
    public SkMap delete(@ApiParam(required = true, name = "id", value
            = "待删除的id") @RequestParam KeyType id, HttpServletRequest request) throws SkException {
        getBaseService().delete(id);
        deleteExtra(id, request);
        return SkMap.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "deleteAll")
    @ApiOperation(value = "通用提交删除接口", notes = "通用提交删除接口", httpMethod = "POST")
    @ResponseBody
    public SkMap deleteAll(@ApiParam(required = true, name = "ids", value
            = "待删除的id", allowMultiple = true) @RequestParam(name = "ids[]") KeyType[] ids, HttpServletRequest request) throws SkException {

        if (ids == null && ids.length == 0)
            return SkMap.fail("message", "删除的条目为空");
        List<KeyType> idsList = Arrays.asList(ids);
        getBaseService().batchDelete(idsList);
        deleteAllExtra(idsList, request);
        return SkMap.ok();
    }

    /**
     * 修改时额外操作
     *
     * @param ids
     */
    public void deleteAllExtra(List<KeyType> ids, HttpServletRequest request) throws SkException {

    }


    @RequestMapping(value = "listToJson", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "通用分页", notes = "通用分页接口")
    @ResponseBody
    public SkMap listToJson(@ApiParam(required = true, name = "ts", value
            = "搜索条件") TS ts,
                            @ApiParam(value = "每页数量", defaultValue = "15", example = "15")
                            @RequestParam(value = "pageSize", defaultValue = "15", required = false) int pageSize,
                            @ApiParam(value = "当前页码", defaultValue = "1", example = "1")
                            @RequestParam(value = "pageNumber", defaultValue = "1", required = false)
                                    int pageNumber, HttpServletRequest request) throws SkException {
        SkMap resultMap = SkMap.ok();
        searchExtra(ts, resultMap, request);
        fullListToJson(ts, pageSize, pageNumber, resultMap, request);
        return resultMap;
    }


    @RequestMapping(value = "listAllJson", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "通用查询集合", notes = "通用查询集合")
    @ResponseBody
    public SkMap listAllJson(@ApiParam(required = true, name = "ts", value
            = "搜索条件") TS ts, HttpServletRequest request) throws SkException {
        return SkMap.ok(Const.PAGINATE_DATA_KEY, mapList(getBaseService().getAllList(ts), getVOClass()));
    }

    protected List<T> decorateList(List<T> dataList) {
        return dataList;
    }


    protected void fullListToJson(TS ts, int pageSize, int pageNumber, SkMap resultMap, HttpServletRequest request) throws SkException {
        ts.setPageNumber(pageNumber);
        ts.setLimit(pageSize);
        Map<String, Object> skMap = getBaseService().paginate(ts);
        //resultMap.set("search", ts);
        resultMap.set(Const.PAGINATE_DATA_KEY, mapList(decorateList((List<T>) skMap.get(PubConst.PAGINATE_DATA_KEY)), getVOClass()));
        resultMap.set(Const.PAGINATE_PAGE_KEY, skMap.get(Const.PAGINATE_PAGE_KEY));
    }
}
