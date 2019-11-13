package vip.sunke.websocket.config;


import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author sunke
 * @Date 2019-05-21 09:05
 * @description websocket 拿到httpSession
 */

public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {


    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

       // HttpSession httpSession = (HttpSession) request.getHttpSession();


     //   sec.getUserProperties().put(HttpSession.class.getName(), httpSession);



        super.modifyHandshake(sec, request, response);
    }
}
