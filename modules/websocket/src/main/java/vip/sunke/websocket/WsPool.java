package vip.sunke.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import vip.sunke.amqp.config.RabbitMqConfig;
import vip.sunke.common.NumberUtil;
import vip.sunke.common.StringUtil;
import vip.sunke.websocket.message.Message;

import java.util.*;

/**
 * @author sunke
 * @Date 2019-05-22 09:45
 * @description
 */
@Slf4j

public class WsPool {


    private static final Map<String, SkWebSocket> wsUserMap = new HashMap<String, SkWebSocket>();
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private static ApplicationContext applicationContext;
    private static StringRedisTemplate redisTemplate;
    private static AmqpTemplate amqpTemplate;
    private static String WEBSOCKET_COUNT_KEY = "websocket_count";


    public static void setApplicationContext(ApplicationContext applicationContext) {
        WsPool.applicationContext = applicationContext;
        redisTemplate = (StringRedisTemplate) WsPool.applicationContext.getBean("stringRedisTemplate");
        amqpTemplate = (AmqpTemplate) WsPool.applicationContext.getBean("rabbitTemplate");
    }

    public static synchronized int getOnlineCount() {
        return NumberUtil.parseInt(redisTemplate.opsForValue().get(WEBSOCKET_COUNT_KEY));
    }

    public static synchronized void addOnlineCount() {

        int onlineCount = NumberUtil.parseInt(redisTemplate.opsForValue().get(WEBSOCKET_COUNT_KEY));

        redisTemplate.opsForValue().set(WEBSOCKET_COUNT_KEY, (onlineCount + 1) + "");

        //  WsPool.onlineCount++;
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    public static synchronized void subOnlineCount() {
        //   WsPool.onlineCount--;
        int onlineCount = NumberUtil.parseInt(redisTemplate.opsForValue().get(WEBSOCKET_COUNT_KEY));
        if (onlineCount >= 0)
            onlineCount = onlineCount - 1;
        else
            onlineCount = 0;

        redisTemplate.opsForValue().set(WEBSOCKET_COUNT_KEY, onlineCount + "");

        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());//在线数减1

    }


    /**
     * 根据userName获取WebSocket,这是一个list,此处取第一个
     * 因为有可能多个websocket对应一个userName（但一般是只有一个，因为在close方法中，我们将失效的websocket连接去除了）
     * userName  用户名
     *
     * @param
     */
    private static SkWebSocket getWsByUser(String userName) {
        Set<String> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (String name : keySet) {
                if (userName.equalsIgnoreCase(name))
                    return wsUserMap.get(name);

            }
        }
        return null;
    }

    /**
     * 向连接池中添加连接
     *
     * @param
     */
    public static void addUser(String userName, SkWebSocket conn, int port) {
        wsUserMap.put(userName, conn); // 添加连接
        WsPool.addOnlineCount();
        redisTemplate.opsForValue().set(userName, port + "");

    }


    /**
     * 获取所有连接池中的用户，因为set是不允许重复的，所以可以得到无重复的user数组
     *
     * @return
     */
    public static Collection<String> getOnlineUser() {
        List<String> setUsers = new ArrayList<String>();
        Collection<String> setUser = wsUserMap.keySet();
        for (String u : setUser) {
            setUsers.add(u);
        }
        return setUsers;
    }

    /**
     * 移除用户
     *
     * @param
     */
    public static boolean removeUser(String username) {

        //说明在当前的容器里
        if (wsUserMap.containsKey(username)) {
            return removeUserInContainer(username);
        }

        String routeKey = RabbitMqConfig.getOffLineRouteKey(NumberUtil.parseInt(redisTemplate.opsForValue().get(username)));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "offline");
        jsonObject.put("username", username);
        amqpTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_LINE_NAME, routeKey, jsonObject.toJSONString());
        return true;
    }

    /**
     * 向特定的用户发送数据
     *
     * @param conn
     * @param message
     */
    private static void sendMessageToUserInContainer(SkWebSocket conn, Message message) {

        if (null != conn && null != wsUserMap.get(conn.getUsername())) {
            message.sendMessage(conn);
        }

    }


    /**
     * 给用户发信息，如果容器里直接发，如果当前容器没有，发消息到rabbitmq
     *
     * @param to
     * @param message
     */
    public static void sendMessageToUser(String to, Message message) {
        if (wsUserMap.containsKey(to)) {
            WsPool.sendMessageToUserInContainer(to, message);
            return;
        }
        String routeKey = RabbitMqConfig.getMessageRouteKey(NumberUtil.parseInt(redisTemplate.opsForValue().get(to)));
        WsPool.amqpTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_MESSAGE_NAME, routeKey, JSON.toJSONString(message));
    }


    /**
     * 给当前容器里用户发信息
     *
     * @param to
     * @param message
     */
    public static void sendMessageToUserInContainer(String to, Message message) {
        SkWebSocket toWebSocket = WsPool.getWsByUser(to);
        if (toWebSocket == null) {
            return;
        }
        WsPool.sendMessageToUserInContainer(toWebSocket, message);

    }

    /**
     * 分别给对方和自己发消息
     *
     * @param message
     */
    private static void sendMessageToUserAndMyselfInContainer(Message message) {
        if (message == null)
            return;

        SkWebSocket conn = WsPool.getWsByUser(message.getTo());
        if (conn != null)
            WsPool.sendMessageToUserInContainer(conn, message);


        conn = WsPool.getWsByUser(message.getFrom());
        if (conn != null)
            WsPool.sendMessageToUserInContainer(conn, message);


    }

    /**
     * 向所有的用户发送消息
     *
     * @param message
     */
    private static void sendMessageToAllInContainer(Message message) {
        Set<String> keySet = wsUserMap.keySet();
        SkWebSocket skWebSocket;
        synchronized (keySet) {
            for (String name : keySet) {
                skWebSocket = wsUserMap.get(name);
                if (StringUtil.isNotObjEmpty(skWebSocket))
                    continue;
                WsPool.sendMessageToUserInContainer(name, message);

            }
        }
    }



    public static boolean removeUserInContainer(String username) {
        wsUserMap.remove(username); // 移除连接
        WsPool.subOnlineCount();
        redisTemplate.delete(username);
        return true;
    }


}
