package vip.sunke.pubInter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.sunke.pubInter.exception.SkException;
import vip.sunke.web.common.SkJsonResult;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;

/**
 * @author sunke
 * @Date 2019/11/13 14:09
 * @description api 接口需要的继承在类
 */

public abstract class RestfulController<
        DTO extends AbstractDTO
        , T extends BaseIdDoMain
        , TS extends BaseSearch
        , DomainVO extends AbstractVO<String>
        , DetailVO extends AbstractDomainVO<DomainVO>
        , ListVO extends PageVO<DomainVO>> extends BaseController {

    public abstract IBaseService<T, TS, String> getBaseService() throws SkException;

    public abstract String getBaseRoute() throws SkException;


    public Class<T> getDomainClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[1];
    }

    public Class<DetailVO> getDetailVOClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[4];
    }


    public Class<ListVO> getListVOClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[5];
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


    @GetMapping("detail/{id}")
    @ResponseBody
    public SkJsonResult<DetailVO> getDetail(@PathVariable String id, HttpServletRequest request) throws SkException {

        T domain = getBaseService().getDetail(id);
        SkJsonResult<DetailVO> jsonResult = SkJsonResult.ok();
        jsonResult.setData(map(new DecorateModel<T>(domain), getDetailVOClass()));
        detailExtend(domain, jsonResult.getData(), request);
        return jsonResult;

    }

    public void detailExtend(T t, DetailVO detailVO, HttpServletRequest request) {

    }


}
