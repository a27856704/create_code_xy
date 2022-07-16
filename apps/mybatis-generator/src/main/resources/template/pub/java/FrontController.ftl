package ${pubPackage}.pubInter;


import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
* @author ${author}
* @Date ${createTime}
* @description 前台controller基础
*/
public class FrontController extends BaseController {
    public static String MY_SECRET = "mysecret";
    public static String USER_HEADER_KEY = "authorization";
    public static String USER_HEADER_KEY_PREFIX = "usertoken ";
    protected void setToken(JSONObject jsonObject, HttpServletResponse response) {
        String token = Jwts.builder()
                .setSubject(JSONObject.toJSONString(jsonObject))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, MY_SECRET)
                .compact();
        response.addHeader(USER_HEADER_KEY, USER_HEADER_KEY_PREFIX + token);
    }



}
