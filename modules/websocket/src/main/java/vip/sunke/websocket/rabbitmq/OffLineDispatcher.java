package vip.sunke.websocket.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import vip.sunke.websocket.WsPool;

/**
 * @author sunke
 * @Date 2019-05-28 15:06
 * @description
 */

@Component
public class OffLineDispatcher {


    @RabbitListener(queues = "${rabbitmq.queues.lineName}")
    public void dispatcher(String message) {

        JSONObject jsonObject = JSONObject.parseObject(message);

        if (jsonObject != null)

            WsPool.removeUserInContainer(jsonObject.getString("username")); // 移除连接


    }


}
