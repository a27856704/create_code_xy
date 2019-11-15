package ${pubPackage}.pubInter;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import ${pubPackage}.web.common.SkJsonResult;

import javax.servlet.http.HttpServletRequest;
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
public abstract class BackController<T extends BaseIdDoMain, TS extends BaseSearch, KeyType> extends BaseController {

    public abstract IBaseService<T, TS, KeyType> getBaseService() throws SkException;

    public abstract String getBaseRoute() throws SkException;

    public abstract String getBaseView() throws SkException;

    public String getMenuModel() {
        return "";
    }

    /**
     * 列表页面视图
     *
     * @return
     * @throws SkException
     */
    public String getListPageView() throws SkException {
        return getBaseView() + "list";
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


    public String getAddPageView() throws SkException {
        return getBaseView() + "add";
    }

    public String getAddPageRoute() throws SkException {
        return getBaseRoute() + "add";
    }


    public String getModPageView() throws SkException {
        return getBaseView() + "mod";
    }

    public String getModPageRoute() throws SkException {
        return getBaseRoute() + "mod";
    }

    public String getPostAddRoute() throws SkException {
        return getBaseRoute() + "postAdd";
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


    public String getDetailPageView() throws SkException {
        return getBaseView() + "detail";
    }

    public String getDetailPageRoute() throws SkException {
        return getBaseRoute() + "detail";
    }

    public String getAddTitle() {
        return "添加";
    }

    public String getDetailTitle() {
        return "详情";
    }

    public String getModTitle() {
        return "修改";
    }

    public String getListTitle() {
        return "列表";
    }

    public String getAddBtnTitle() {
        return "添加";
    }

    public String getModBtnTitle() {
        return "修改";
    }

    public String getDetailBtnTitle() {
        return "详情";
    }

    public String getDeleteBtnTitle() {
        return "删除";
    }


    /**
     * 添加页面
     *
     * @param domain
     * @param model
     * @param session
     * @return
     * @throws SkException
     */
    @ApiOperation(value = "添加页面", hidden = true)
    @GetMapping("add")
    public String add(T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        model.addAttribute("domain", domain);
        model.addAttribute("addTitle", getAddTitle());
        model.addAttribute("action", getPostAddRoute());
        addPageExtend(domain, model, request, session);
        return getAddPageView();
    }

    /**
     * 添加页面附加数据
     *
     * @param domain
     * @param model
     * @param session
     * @throws SkException
     */
    public void addPageExtend(T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        pageExtend(domain, model, request, session);
    }

    /**
     * 添加修改列表页面添加数据
     *
     * @param domain
     * @param model
     * @param session
     * @throws SkException
     */
    public void pageExtend(T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        model.addAttribute("listPage", getListPageRoute());
        model.addAttribute("menuModel", getMenuModel());
    }


    /**
     * 添加数据前检测
     *
     * @param domain
     * @param model
     * @param session
     * @return
     * @throws SkException
     */
    public boolean beforePostAdd(T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        return true;
    }

    /**
     * 添加插入数据后的操作
     *
     * @param domain
     * @param model
     * @param session
     * @throws SkException
     */
    public void afterPostAdd(T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {

    }


    /**
     * 添加提交
     *
     * @param domain
     * @param model
     * @param session
     * @return
     * @throws SkException
     */
    @ApiOperation(value = "添加提交数据", httpMethod = "POST")
    @PostMapping("postAdd")
    @ResponseBody
    public SkJsonResult<String> postAdd(@Valid T domain, @ApiParam(hidden = true) @RequestParam(required = false) Model model, @ApiParam(hidden = true) @RequestParam(required = false) HttpServletRequest request, @ApiParam(hidden = true) @RequestParam(required = false) HttpSession session) throws SkException {
        if (!beforePostAdd(domain, model, request, session)) {
            return SkJsonResult.fail("beforePostAdd false;");
        }
        getBaseService().insert(domain);
        afterPostAdd(domain, model, request, session);
        return SkJsonResult.ok();
    }


    /**
     * 修改页面附加数据
     *
     * @param domain
     * @param model
     * @param session
     * @throws SkException
     */
    public void modPageExtend(T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        pageExtend(domain, model, request, session);
    }

    @ApiOperation(value = "修改页面", hidden = true)
    @GetMapping("mod/{id}")
    public String mod(@PathVariable KeyType id, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        T domain = getBaseService().getDetail(id);
        model.addAttribute("domain", domain);
        model.addAttribute("modTitle", getModTitle());
        model.addAttribute("action", getPostModRoute() + "/" + domain.getId());
        modPageExtend(domain, model, request, session);
        return getModPageView();
    }

    /**
     * 修改提交数据
     *
     * @param id
     * @param domain
     * @param model
     * @param session
     * @return
     * @throws SkException
     */
    @ApiOperation(value = "修改提交数据", httpMethod = "POST")
    @PostMapping("postMod/{id}")
    @ResponseBody
    public SkJsonResult<String> postMod(@ApiParam("主键ID") @PathVariable KeyType id, @Valid T domain, @ApiParam(hidden = true) @RequestParam(required = false) Model model, @ApiParam(hidden = true) @RequestParam(required = false) HttpServletRequest request, @ApiParam(hidden = true) @RequestParam(required = false) HttpSession session) throws SkException {
        if (!beforePostMod(domain, model, request, session)) {
            return SkJsonResult.fail("beforePostMod false;");
        }
        T oldDomain = getBaseService().getDetail(id);
        org.springframework.beans.BeanUtils.copyProperties(domain, oldDomain, postModNoUpdate(oldDomain));
        getBaseService().update(oldDomain);
        afterPostMod(oldDomain, model, request, session);
        return SkJsonResult.ok();

    }

    /**
     * 排除不要修改的字段
     *
     * @param domain
     * @return
     */
    private String[] postModNoUpdate(T domain) {
        String[] excludeFiledArr = BeanUtils.getNullPropertyNames(domain);
        if (excludeFiledArr == null)
            excludeFiledArr = new String[0];
        String excludeFiledStr = addExcludeUpdateField(domain);
        if (excludeFiledStr == null || "".equals(excludeFiledStr))
            return excludeFiledArr;
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
     * @param domain
     * @param model
     * @param session
     * @return
     * @throws SkException
     */
    public boolean beforePostMod(T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        return true;
    }

    /**
     * 修改插入数据后的操作
     *
     * @param domain
     * @param model
     * @param session
     * @throws SkException
     */
    public void afterPostMod(T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {

    }


    /**
     * 删除数据前
     *
     * @param id
     * @param model
     * @param session
     * @return
     * @throws SkException
     */
    public boolean beforePostDelete(KeyType id, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        return true;
    }

    /**
     * 删除数据后的操作
     *
     * @param id
     * @param model
     * @param session
     * @throws SkException
     */
    public void afterPostDelete(KeyType id, Model model, HttpServletRequest request, HttpSession session) throws SkException {

    }

    /**
     * 删除单个
     *
     * @param id
     * @return
     * @throws SkException
     */
    @ApiOperation(value = "删除单个", httpMethod = "POST")
    @PostMapping("postDelete")
    @ResponseBody
    public SkJsonResult<String> postDelete(@ApiParam("主键ID") @RequestParam("id") KeyType id, @ApiParam(hidden = true) @RequestParam(required = false) Model model, @ApiParam(hidden = true) @RequestParam(required = false) HttpServletRequest request, @ApiParam(hidden = true) @RequestParam(required = false) HttpSession session) throws SkException {
        if (!beforePostDelete(id, model, request, session)) {
            return SkJsonResult.fail("beforePostDelete false");
        }
        getBaseService().delete(id);
        afterPostDelete(id, model, request, session);
        return SkJsonResult.ok();
    }


    /**
     * 删除数据前
     *
     * @param ids
     * @param model
     * @param session
     * @return
     * @throws SkException
     */
    public boolean beforePostDeleteAll(List<KeyType> ids, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        return true;
    }

    /**
     * 删除数据后的操作
     *
     * @param ids
     * @param model
     * @param session
     * @throws SkException
     */
    public void afterPostDeleteAll(List<KeyType> ids, Model model, HttpServletRequest request, HttpSession session) throws SkException {

    }


    /**
     * 删除
     *
     * @param ids
     * @return
     * @throws
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST")
    @PostMapping(value = "postDeleteAll")
    @ResponseBody
    public SkJsonResult<String> postDeleteAll(@ApiParam("ids") @RequestParam(name = "ids[]") List<KeyType> ids, @ApiParam(hidden = true) @RequestParam(required = false) Model model, @ApiParam(hidden = true) @RequestParam(required = false) HttpServletRequest request, @ApiParam(hidden = true) @RequestParam(required = false) HttpSession session) throws SkException {

        if (ids == null || ids.size() == 0)
            return SkJsonResult.fail("删除的条目为空");
        if (beforePostDeleteAll(ids, model, request, session)) {
            return SkJsonResult.fail("beforePostDeleteAll false");
        }
        getBaseService().batchDelete(ids);
        afterPostDeleteAll(ids, model, request, session);
        return SkJsonResult.ok();
    }


    /**
     * 列表页面增加
     *
     * @param search
     * @param model
     * @param session
     * @throws SkException
     */
    public void listPageExtend(TS search, Model model, HttpServletRequest request, HttpSession session) throws SkException {


    }


    /**
     * 搜索条件附加
     *
     * @param search
     * @throws SkException
     */
    public void searchConditionExtend(TS search, HttpServletRequest request, HttpSession session) throws SkException {


    }


    /**
     * 列表页面
     *
     * @param search
     * @param model
     * @param session
     * @return
     * @throws SkException
     */
    @ApiOperation(value = "列表页面", hidden = true)
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(TS search, Model model, HttpServletRequest request, HttpSession session) throws SkException {

        searchConditionExtend(search, request, session);//附加搜索条件
        model.addAttribute("listTitle", getListTitle());
        model.addAttribute("menuModel", getMenuModel());
        listExtendBtnTitle(model);
        fillListData(search, model, request, session);
        allPageRouteToListPage(model, request, session);
        listPageExtend(search, model, request, session);
        return getListPageView();
    }


    public void listExtendBtnTitle(Model model) {

        model.addAttribute("addBtnTitle", getAddBtnTitle());
        model.addAttribute("modBtnTitle", getModBtnTitle());
        model.addAttribute("detailBtnTitle", getDetailBtnTitle());
        model.addAttribute("deleteBtnTitle", getDeleteBtnTitle());

    }


    /**
     * 列表数据
     *
     * @param search
     * @param model
     * @param request
     * @param session
     * @throws SkException
     */
    public void fillListData(TS search, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        Map<String, Object> skMap = getBaseService().paginate(search);
        model.addAttribute("search", search);
        model.addAttribute(Const.PAGINATE_DATA_KEY, decorateList((List<T>) skMap.get(PubConst.PAGINATE_DATA_KEY)));
        model.addAttribute(Const.PAGINATE_PAGE_KEY, skMap.get(Const.PAGINATE_PAGE_KEY));
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
     * 返回所有的路由
     *
     * @param model
     * @param session
     * @throws SkException
     */
    public void allPageRouteToListPage(Model model, HttpServletRequest request, HttpSession session) throws SkException {
        model.addAttribute("listPage", getListPageRoute());
        model.addAttribute("addPage", getAddPageRoute());
        model.addAttribute("modPage", getModPageRoute());
        model.addAttribute("detailPage", getDetailPageRoute());
        model.addAttribute("deleteAction", getPostDelRoute());
        model.addAttribute("deleteAllAction", getPostDelAllRoute());
    }


    /**
     * 详情页面
     *
     * @param id
     * @param model
     * @param request
     * @param session
     * @return
     * @throws SkException
     */
    @ApiOperation(value = "详情页面", hidden = true)
    @GetMapping("detail/{id}")
    public String detail(@PathVariable KeyType id, Model model, HttpServletRequest request, HttpSession session) throws SkException {

        T domain = getBaseService().getDetail(id);
        model.addAttribute("domain", domain);
        model.addAttribute("title", getDetailTitle());
        detailPageExtend(domain, model, request, session);
        return getDetailPageView();
    }


    /**
     * 详情页面附加数据
     *
     * @param domain
     * @param model
     * @param session
     * @throws SkException
     */
    public void detailPageExtend(T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        model.addAttribute("action", getModPageRoute() + "/" + domain.getId());
        pageExtend(domain, model, request, session);
    }
}
