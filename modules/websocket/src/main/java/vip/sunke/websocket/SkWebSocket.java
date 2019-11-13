package vip.sunke.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.sunke.amqp.config.RabbitMqConfig;
import vip.sunke.websocket.config.HttpSessionConfigurator;
import vip.sunke.websocket.message.Message;
import vip.sunke.websocket.message.MessageContentTypeEnum;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;

/**
 * @author sunke
 * @Date 2019-05-20 14:13
 * @description
 */
@ServerEndpoint(value = "/skWebsocket/{username}", configurator = HttpSessionConfigurator.class)
@Component
@Slf4j
public class SkWebSocket {


    private Session session;
    private String username;//当前用户

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     * @param config  EndpointConfig config
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {


        //  HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        // String username = (String) httpSession.getAttribute("user");

        setSession(session);
        setUsername(username);
        WsPool.addUser(username, this, RabbitMqConfig.getPort());
        Message textMessage = new Message(username, username, new Date());
        textMessage.setContentType(MessageContentTypeEnum.TEXT.getType());
        textMessage.setData("欢迎您" + username);
        textMessage.setTarget(false);
        WsPool.sendMessageToUser(username, textMessage);

    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        WsPool.removeUser(this.getUsername());
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param messageJson 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String messageJson) {
        log.debug("websocket on message:" + messageJson);
        JSONObject jsonObject = JSON.parseObject(messageJson);
        jsonObject.put("from", getUsername());
        jsonObject.put("sendTime", new Date());
        //  int contentType = jsonObject.getIntValue("contentType");
        Message message = jsonObject.toJavaObject(Message.class);
        WsPool.sendMessageToUser(message.getTo(), message);
        WsPool.sendMessageToUser(message.getFrom(), message);
    }


    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Throwable error) {
        log.debug("发生错误" + error.getMessage());
        error.printStackTrace();
    }

}
