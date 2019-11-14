package vip.sunke.pubInter;

import vip.sunke.pubInter.exception.BusinessException;
import vip.sunke.pubInter.exception.SkException;
import vip.sunke.web.common.SkJsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author sunke
 * @Date 2019/11/14 13:40
 * @description 只查询继承这个类
 */

public abstract class ReadRestfulController<DTO extends AbstractDTO
        , T extends BaseIdDoMain
        , TS extends BaseSearch
        , DomainVO extends AbstractDomainVO<String>
        , DataVO extends AbstractDataVO<DomainVO>
        , ListVO extends AbstractPageVO<DomainVO>> extends RestfulController<DTO, T, TS, DomainVO, DataVO, ListVO> {


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
