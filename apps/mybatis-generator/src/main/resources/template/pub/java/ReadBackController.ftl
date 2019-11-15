package ${pubPackage}.pubInter;

import org.springframework.stereotype.Controller;
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
* @description 只查询
*/

@Controller
public abstract class ReadBackController<T extends BaseIdDoMain, TS extends BaseSearch, KeyType> extends BackController<T, TS, KeyType> {


    @Override
    public SkJsonResult<String> postAdd(@Valid T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }

    @Override
    public SkJsonResult<String> postMod(KeyType id, @Valid T domain, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }

    @Override
    public SkJsonResult<String> postDelete(KeyType id, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }

    @Override
    public SkJsonResult<String> postDeleteAll(List<KeyType> ids, Model model, HttpServletRequest request, HttpSession session) throws SkException {
        throw new BusinessException("非法操作");
    }
}