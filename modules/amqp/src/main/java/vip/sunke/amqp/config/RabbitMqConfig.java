package vip.sunke.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/**
 * @author sunke
 * @Date 2019-05-27 13:41
 * @description
 */

@Configuration

public class RabbitMqConfig {


   // private static String QUEUE_MESSAGE_NAME = "direct.message.queue";
    public final static String EXCHANGE_MESSAGE_NAME = "direct_message_exchange";

  //  private static String QUEUE_LINE_NAME = "direct.line.queue";
    public final static String EXCHANGE_LINE_NAME = "direct_line_exchange";

    private static int port;
    @Resource
    private Queue queueMessage;
    @Resource
    private Queue queueLine;
    @Resource
    private DirectExchange exchangeMessage;
    @Resource
    private DirectExchange exchangeLine;

    public static int getPort() {
        return RabbitMqConfig.port;
    }

    @Value("${server.port}")
    public void setPort(int port) {
        RabbitMqConfig.port = port;
    }

    public static String getRouteKeyPrefix() {
        return "direct";
    }

    /**
     * 消息内容路由
     *
     * @param port
     * @return
     */
    public static String getMessageRouteKey(int port) {
        return getRouteKeyPrefix() + ".message." + port;
    }

    /*
    下线消息
     */
    public static String getOffLineRouteKey(int port) {
        return getRouteKeyPrefix() + ".line." + port;
    }

    @Bean
    public Queue queueMessage(@Value("${rabbitmq.queues.messageName}") String queueName) {
        return new Queue(queueName);
    }

    @Bean
    public Queue queueLine(@Value("${rabbitmq.queues.lineName}") String queueName) {
        return new Queue(queueName);
    }

    @Bean
    public DirectExchange exchangeMessage() {
        return new DirectExchange(EXCHANGE_MESSAGE_NAME);
    }


    @Bean
    public DirectExchange exchangeLine() {
        return new DirectExchange(EXCHANGE_LINE_NAME);
    }

    @Bean
    public Binding bindingExchangeMessage() {

        return BindingBuilder.bind(queueMessage).to(exchangeMessage).with(RabbitMqConfig.getMessageRouteKey(RabbitMqConfig.getPort()));
    }


    @Bean
    public Binding bindingExchangeOffLine() {
        return BindingBuilder.bind(queueLine).to(exchangeLine).with(RabbitMqConfig.getOffLineRouteKey(RabbitMqConfig.getPort()));
    }


}
