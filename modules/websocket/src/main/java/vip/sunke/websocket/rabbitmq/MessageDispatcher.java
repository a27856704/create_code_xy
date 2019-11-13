package vip.sunke.websocket.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import vip.sunke.amqp.ReceiveDispatcher;
import vip.sunke.websocket.WsPool;
import vip.sunke.websocket.message.Message;

/**
 * @author sunke
 * @Date 2019-05-28 10:22
 * @description
 */

@Component(value = "messageDispatcher")
public class MessageDispatcher implements ReceiveDispatcher {


    @Override
    public void dispatcher(String message) {

        Message textMessage = JSON.parseObject(message, Message.class);


        if (textMessage.isTarget()) {
            WsPool.sendMessageToUserInContainer(textMessage.getTo(), textMessage);
        } else {
            WsPool.sendMessageToUserInContainer(textMessage.getFrom(), textMessage);
        }


        //   WsPool.sendMessageToUserAndMyself(textMessage);


    }
}
