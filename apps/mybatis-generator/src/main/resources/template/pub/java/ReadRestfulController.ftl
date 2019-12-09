package ${pubPackage}.pubInter;

import org.springframework.ui.Model;
import ${pubPackage}.pubInter.exception.BusinessException;
import ${pubPackage}.pubInter.exception.SkException;
import ${pubPackage}.web.common.SkJsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author ${author}
 * @Date ${createTime}
 * @description 只查询继承这个类
 */

public abstract class ReadRestfulController<DTO extends AbstractDTO
        , T extends BaseIdDoMain
        , TS extends BaseSearch
        , DomainVO extends AbstractDomainVO<String>
        , DetailDomainVO extends DomainVO
        , DataVO extends AbstractDataVO<DetailDomainVO>
        , ListVO extends AbstractPageVO<DomainVO>> extends RestfulController<DTO, T, TS, DomainVO, DetailDomainVO,DataVO, ListVO> {


    @Override
    public SkJsonResult<DataVO> postAdd(@Valid DTO dto, HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }

    @Override
    public SkJsonResult<DataVO> postMod(String id, @Valid DTO dto,  HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }

    @Override
    public SkJsonResult postDelete(String id, HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }

    @Override
    public SkJsonResult postDeleteAll(List<String> ids,  HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }
}