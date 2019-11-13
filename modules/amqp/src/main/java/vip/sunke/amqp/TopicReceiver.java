package vip.sunke.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sunke
 * @Date 2019-05-27 14:54
 * @description
 */
@Service
public class TopicReceiver {


    @Autowired
    private ReceiveDispatcher dispatcher;
    @RabbitListener(queues = "${rabbitmq.queues.messageName}")
    public void receiveMessage(String str) {
        dispatcher.dispatcher(str);
    }


}
