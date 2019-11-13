package vip.sunke.createdb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.sunke.pubInter.exception.BusinessException;
import vip.sunke.web.common.Const;
import vip.sunke.web.common.SkMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author sunke
 * @Date 2018/8/6 09:28
 * @description
 */

@Controller
public class IndexController {


    @GetMapping("/login")
    public String login() throws BusinessException {

        return "login";

    }

    @PostMapping("/login")
    @ResponseBody
    public SkMap login(String username, String password, HttpSession session) throws BusinessException {

        if (!"sunke".equalsIgnoreCase(username) || !"sunke".equalsIgnoreCase(password)) {
            throw new BusinessException("用户名或密码错误");
        }

        session.setAttribute(Const.USER_SESSION, username);


        return SkMap.ok();

    }


    @GetMapping("/index")
    public String index() throws BusinessException {


        return "index";

    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response, HttpSession session) throws BusinessException, IOException {

        session.removeAttribute(Const.USER_SESSION);

        response.sendRedirect("/login");

    }


}
