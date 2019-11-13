package vip.sunke.websocket.listener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sunke
 * @Date 2019-05-21 09:14
 * @description
 */

@Component
public class RequestListener implements ServletRequestListener {


    /**
     * The request is about to go out of scope of the web application.
     *
     * @param sre Information about the request
     */
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    /**
     * The request is about to come into scope of the web application.
     *
     * @param sre Information about the request
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {

        //将所有request请求都携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();








    }
}
