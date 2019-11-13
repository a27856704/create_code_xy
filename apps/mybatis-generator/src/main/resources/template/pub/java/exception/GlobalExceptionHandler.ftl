package ${pubPackage}.pubInter.exception;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

/**
* @author ${author}
* @Date ${createTime}
* @description 异常全局捕获
*/


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    private boolean debug=true;

    @ExceptionHandler(value = Exception.class)
    public ModelAndView businessExceptionHandler(HttpServletRequest req, HttpServletResponse response, Exception ex) throws Exception {

        int code = -1;
        String msg = "";

        log.debug("异常：" + ex.getMessage());

        if (debug)
            ex.printStackTrace();
        msg = ex.getMessage();
        if (ex != null && ex.getMessage() != null) {

            if ((ex instanceof ConstraintViolationException)) {

                try {
                    msg = ((ConstraintViolationException) ex).getConstraintViolations().iterator().next().getMessageTemplate();
                } catch (Exception e) {
                    msg = ex.getMessage();
                }


            } else if (ex instanceof BindException) {
                try {
                    msg = ((BindException) ex).getBindingResult().getAllErrors().get(0).getDefaultMessage();
                } catch (Exception e) {

                }
            }


            String[] temp = msg.split("@@");
            if (temp != null && temp.length > 1) {
                try{
                    code =new Integer(temp[1]).intValue();
                }catch(Exception eInt){
                    code=-1;
                }
                msg = temp[0];

                log.debug("异常：" + ex.getMessage());

            }
            /*else {
               // msg = ex.getMessage();


            }*/
        }

        String XmlHttpRequest = req.getHeader("x-requested-with");

        String contentType = req.getContentType();


        String jsonType = req.getHeader("jsonType");
        if (("XMLHttpRequest").equalsIgnoreCase(XmlHttpRequest) || "jsonType".equalsIgnoreCase(jsonType)) {

            try {
                response.setContentType("application/json;charset = UTF-8");

                JSONObject resultJson = new JSONObject();
                resultJson.put("errorCode", code);
                resultJson.put("message", msg);
                resultJson.put("success", false);
                JSONObject dataJson = new JSONObject();
                dataJson.put("code", code);
                resultJson.put("data", dataJson);

                response.getWriter().print(JSONObject.toJSONString(resultJson));

                response.getWriter().flush();
                response.getWriter().close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //return null;
            return new ModelAndView();
        }


        ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);


        // System.out.println( ex.getMessage());
        mav.getModel().put("message", "错误原因:" + ex.getMessage());


        return mav;
    }


}
