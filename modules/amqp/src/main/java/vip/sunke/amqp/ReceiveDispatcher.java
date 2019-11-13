package vip.sunke.amqp;

/**
 * @author sunke
 * @Date 2019-05-28 10:18
 * @description 消息分发器
 */

public interface ReceiveDispatcher {


    void dispatcher(String message);

}
