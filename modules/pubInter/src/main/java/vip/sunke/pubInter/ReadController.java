package vip.sunke.pubInter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import vip.sunke.pubInter.exception.BusinessException;
import vip.sunke.pubInter.exception.SkException;
import vip.sunke.web.common.SkMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author sunke
 * @Date 2019/11/1 15:47
 * @description 只查询
 */

@Controller
public abstract class ReadController<T extends BaseIdDoMain, TS extends BaseSearch, KeyType> extends BackController<T, TS, KeyType> {


    @Override
    public SkMap postAdd(@Valid T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }

    @Override
    public SkMap postMod(KeyType id, @Valid T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }

    @Override
    public SkMap postDel(KeyType id, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }

    @Override
    public SkMap postDeleteAll(List<KeyType> ids, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }
}
