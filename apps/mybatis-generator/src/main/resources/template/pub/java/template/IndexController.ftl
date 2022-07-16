package ${package};


import ${commonPackage}.Const;
import ${commonPackage}.NumberUtil;
import ${commonPackage}.StringUtil;
import ${commonPackage}.enums.WhetherEnum;
import ${modelExtPackage}.AdminExt;
import ${pubInterPackage}.exception.BusinessException;
import ${pubInterPackage}.exception.SkException;
import ${servicePackage}.IAdminService;
import ${webCommonPackage}.SecurityUtil;
import ${webCommonPackage}.SkMap;
import ${webCommonPackage}.exceptionEnums.AdminUserExceptionEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
*    @author ${author}
*    @Date ${date}
*    @description
*/

@Controller("backIndexController")


public class IndexController {

    @Resource
    private IAdminService adminService;


    @RequestMapping("/")
    public String index() throws SkException {
        return "redirect:${route}admin/list";
    }


    @GetMapping("/login")
    public String login() throws SkException {
        return "${route}admin/login";
    }
    @GetMapping("/modPsw")
    public String modPsw(Model model) throws SkException {
        model.addAttribute("action", "/postPsw");
        model.addAttribute("listPage", "/modPsw");
        model.addAttribute("menuModel", "管理员管理");
        model.addAttribute("addTitle", "修改密码");
        model.addAttribute("menuModelName", "admin");
        model.addAttribute("menuModelPage", "admin/list");
        return "${route}admin/psw";
    }

    @PostMapping("/postPsw")
    @ResponseBody
    public SkMap postPsw(String oldPassword, String password, String passwordConfirm , HttpSession session) throws SkException {

        if (StringUtil.isNullOrEmpty(passwordConfirm) || !passwordConfirm.equalsIgnoreCase(password)) {
            throw new BusinessException(AdminUserExceptionEnum.ADMIN_PWD_ERROR);
        }

        AdminExt admin = (AdminExt) session.getAttribute(Const.ADMIN_SESSION);
        if (!SecurityUtil.md5(oldPassword).equalsIgnoreCase(admin.getPassword())) {
            throw new BusinessException(AdminUserExceptionEnum.ADMIN_PWD_ERROR);
        }
        admin.setPassword(SecurityUtil.md5(password));
        adminService.update(admin);
        session.setAttribute(Const.ADMIN_SESSION, admin);
        return SkMap.ok();
    }




    @PostMapping("/login")
    @ResponseBody
    public SkMap postLogin(String username, String password, HttpSession session) throws SkException {

        if (StringUtil.isEmpty(username)) {
            throw new BusinessException(AdminUserExceptionEnum.ADMIN_NAME_EMPTY);
        }

        if (StringUtil.isEmpty(password)) {
            throw new BusinessException(AdminUserExceptionEnum.ADMIN_PWD_EMPTY);
        }

        AdminExt admin = adminService.getDetailBySearch(AdminSearchExt.getInstance().setUsernameEq(username));
        if (admin == null) {
            throw new BusinessException(AdminUserExceptionEnum.ADMIN_NAME_NO_EXIST);
        }

        if (NumberUtil.parseInt(admin.getForbidden()) == WhetherEnum.YES.getValue()) {
            throw new BusinessException(AdminUserExceptionEnum.ADMIN_DISABLE);
        }

        if (!SecurityUtil.md5(password).equalsIgnoreCase(admin.getPassword())) {
            throw new BusinessException(AdminUserExceptionEnum.ADMIN_PWD_ERROR);
        }

        session.setAttribute(Const.ADMIN_SESSION, admin);


        return SkMap.ok();
    }



    @RequestMapping("/logout")
    public String logout(HttpSession session) throws SkException {


        session.removeAttribute(Const.ADMIN_SESSION);
        return "redirect:/login";


    }

    @RequestMapping("/systemError")
    public String error(String msg, Model model) throws SkException {
        model.addAttribute("msg", msg);
        return "${route}error";
    }


}
