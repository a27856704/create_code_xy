package vip.sunke.template.controller.front;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vip.sunke.pubInter.FrontController;
import vip.sunke.pubInter.exception.BusinessException;

import javax.servlet.http.HttpSession;

/**
 * @author sunke
 * @Date 2019-05-20 14:22
 * @description
 */

@Controller(value = "websocket")
public class WebSocketController extends FrontController {


    @Value("${aaa}")
    private String aaa;


    @GetMapping("/index")
    public String index(String from, Model model, HttpSession session) throws BusinessException {


        session.setAttribute("from", from);
        model.addAttribute("from", from);
        return "websocket";


    }


}
