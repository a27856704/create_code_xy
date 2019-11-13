package ${pubPackage}.pubInter;


import org.apache.commons.lang3.ArrayUtils;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ${pubPackage}.common.BeanUtils;
import ${pubPackage}.common.IdGen;
import ${pubPackage}.common.StringUtil;
import ${pubPackage}.pubInter.common.PubConst;
import ${pubPackage}.pubInter.exception.BusinessException;
import ${pubPackage}.pubInter.exception.BusinessExceptionEnum;
import ${pubPackage}.pubInter.exception.SkException;
import ${pubPackage}.web.common.Const;
import ${pubPackage}.web.common.SkMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ${author}
 * @Date ${createTime}
 * @description 完成基本增删改详情列表等操作
 */
@Validated
public abstract class BackController<T extends BaseIdDoMain, TS extends BaseSearch, KeyType> extends BaseController implements IShowMenu {

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
    public void addExtra(T t, HttpSession session) throws SkException {
        if (t.getId() instanceof String) {
            if (StringUtil.isEmpty((String) t.getId())) {
                t.setId((KeyType) IdGen.uuid());
            }
        }
    }

    /**
     * 修改时额外数据组装
     *
     * @param t
     */
    public void modExtra(T t, HttpSession session) throws SkException {


    }

    /**
     * 修改时额外操作
     *
     * @param id
     */
    public void deleteExtra(KeyType id, HttpSession session) throws SkException {

    }

    public void searchExtra(TS ts, Model model, HttpSession session) throws SkException {
        pageExtra(model, session);
    }

    /**
     * 添加页面
     *
     * @param model
     * @throws BusinessException
     */
    public void addPageExtra(T t, Model model, HttpSession session) throws SkException {
        pageExtra(model, session);
    }

    public void pageExtra(Model model, HttpSession session) {
        model.addAttribute("currentMenu", getCurrentMenu());
        model.addAttribute("menuModel", getMenuModel());
    }

    /**
     * 添加页面
     *
     * @param t
     * @param model
     * @param session
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(T t, Model model, HttpSession session) throws SkException {
        addPageExtra(t, model, session);
        model.addAttribute("domain", t);
        model.addAttribute("action", getBaseRoute() + "postAdd");
        return getBaseView() + "add";
    }

    /**
     * 添加提交
     *
     * @param t
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "postAdd", method = RequestMethod.POST)
    @ResponseBody
    public SkMap postAdd(@Valid T t, HttpSession session) throws SkException {
        if (t == null)
            return SkMap.ok();
        addExtra(t, session);
        getBaseService().insert(t);
        return SkMap.ok();
    }

    /**
     * 修改页面附加内容
     *
     * @param model
     * @throws BusinessException
     */
    public void modPageExtra(T t, Model model, HttpSession session) throws SkException {
        pageExtra(model, session);
    }

    /**
     * 修改页面
     *
     * @param id
     * @param model
     * @param session
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "mod/{id}", method = RequestMethod.GET)
    public String mod(@PathVariable KeyType id, Model model, HttpSession session) throws SkException {
        T t = getBaseService().getDetail(id);
        model.addAttribute("domain", t);
        model.addAttribute("action", getBaseRoute() + "postMod/" + id);
        modPageExtra(t, model, session);
        return getBaseView() + "mod";
    }


    /**
     * 详情页面
     *
     * @param id
     * @param model
     * @param session
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable KeyType id, Model model, HttpSession session) throws SkException {
        T t = getBaseService().getDetail(id);
        model.addAttribute("domain", t);
        model.addAttribute("modPage", getBaseRoute() + "mod/" + id);
        detailPageExtra(t, model, session);
        return getBaseView() + "detail";
    }


    /**
     * 详情页面
     *
     * @param id
     * @param session
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "detailToJson/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SkMap detailToJson(@PathVariable KeyType id, HttpSession session) throws SkException {
        T t = getBaseService().getDetail(id);
        SkMap resultMap = SkMap.ok();
        resultMap.set("domain", t);
        resultMap.set("modPage", getBaseRoute() + "mod/" + id);
        detailPageExtraToMap(t, resultMap, session);
        return resultMap;
    }


    /**
     * 详情页面附加内容
     *
     * @param resultMap
     * @throws BusinessException
     */
    public void detailPageExtraToMap(T t, SkMap resultMap, HttpSession session) throws SkException {
    }

    /**
     * 详情页面附加内容
     *
     * @param model
     * @throws BusinessException
     */
    public void detailPageExtra(T t, Model model, HttpSession session) throws SkException {
    }

    /**
     * 修改提交
     *
     * @param id
     * @param t
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "postMod/{id}", method = RequestMethod.POST)
    @ResponseBody
    public SkMap postMod(@PathVariable KeyType id, @Valid T t, HttpSession session) throws SkException {
        if (t == null)
            return SkMap.ok();
        modExtra(t, session);
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
    @RequestMapping(value = "postDelete", method = RequestMethod.POST)
    @ResponseBody
    public SkMap postDelete(@RequestParam KeyType id, HttpSession session) throws SkException {
        getBaseService().delete(id);
        deleteExtra(id, session);
        return SkMap.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "postDeleteAll", method = RequestMethod.POST)
    @ResponseBody
    public SkMap postDelete(@RequestParam(name = "ids[]") KeyType[] ids, HttpSession session) throws SkException {

        if (ids == null && ids.length == 0)
            return SkMap.fail("message", "删除的条目为空");
        List<KeyType> idsList = Arrays.asList(ids);
        getBaseService().batchDelete(idsList);
        deleteAllExtra(idsList, session);
        return SkMap.ok();
    }

    /**
     * 修改时额外操作
     *
     * @param ids
     */
    public void deleteAllExtra(List<KeyType> ids, HttpSession session) throws SkException {

    }


    /**
     * 列表页面
     *
     * @param ts
     * @param pageSize
     * @param pageNumber
     * @param model
     * @param session
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "list")
    public String list(TS ts,
                       @RequestParam(value = "pageSize", defaultValue = "15", required = false) int pageSize,
                       @RequestParam(value = "pageNumber", defaultValue = "1", required = false)
                               int pageNumber, Model model, HttpSession session) throws SkException {
        model.addAttribute("currentMenu", getCurrentMenu());
        model.addAttribute("menuModel", getMenuModel());
        model.addAttribute("addTitle", getAddTitle());
        model.addAttribute("modTitle", getModTitle());
        searchExtra(ts, model, session);
        fullList(ts, pageSize, pageNumber, model);
        return getBaseView() + "list";
    }


    @RequestMapping(value = "listToJson")
    @ResponseBody
    public SkMap listToJson(TS ts,
                            @RequestParam(value = "pageSize", defaultValue = "15", required = false) int pageSize,
                            @RequestParam(value = "pageNumber", defaultValue = "1", required = false)
                                    int pageNumber, Model model, HttpSession session) throws SkException {

        searchExtra(ts, model, session);
        SkMap resultMap = SkMap.ok();
        fullListToJson(ts, pageSize, pageNumber, resultMap);
        return resultMap;
    }


    @RequestMapping(value = "listAllJson")
    @ResponseBody
    public SkMap listAllJson(TS ts) throws SkException {
        return SkMap.ok(Const.PAGINATE_DATA_KEY, getBaseService().getAllList(ts));
    }

    protected List<T> decorateList(List<T> dataList) {
        return dataList;
    }


    protected void fullListToJson(TS ts, int pageSize, int pageNumber, SkMap resultMap) throws SkException {
        ts.setPageNumber(pageNumber);
        ts.setLimit(pageSize);
        Map<String, Object> skMap = getBaseService().paginate(ts);
        resultMap.set("search", ts);
        resultMap.set(Const.PAGINATE_DATA_KEY, decorateList((List<T>) skMap.get(PubConst.PAGINATE_DATA_KEY)));
        resultMap.set(Const.PAGINATE_PAGE_KEY, skMap.get(Const.PAGINATE_PAGE_KEY));
    }

    protected void fullList(TS ts, int pageSize, int pageNumber, Model model) throws SkException {
        ts.setPageNumber(pageNumber);
        ts.setLimit(pageSize);
        Map<String, Object> skMap = getBaseService().paginate(ts);
        model.addAttribute("search", ts);
        model.addAttribute(Const.PAGINATE_DATA_KEY, decorateList((List<T>) skMap.get(PubConst.PAGINATE_DATA_KEY)));
        model.addAttribute(Const.PAGINATE_PAGE_KEY, skMap.get(Const.PAGINATE_PAGE_KEY));
        setAllAction(model);
    }

    /**
     * 添加action
     *
     * @param model
     */
    protected void setAllAction(Model model) {
        model.addAttribute("listPage", getBaseRoute() + "list");
        model.addAttribute("addPage", getBaseRoute() + "add");
        model.addAttribute("modPage", getBaseRoute() + "mod");
        model.addAttribute("detailPage", getBaseRoute() + "detail");
        model.addAttribute("deleteAction", getBaseRoute() + "postDelete");
        model.addAttribute("delAllPage", getBaseRoute() + "postDeleteAll");
    }
}
