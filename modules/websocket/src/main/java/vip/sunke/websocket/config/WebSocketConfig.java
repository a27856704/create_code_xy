package vip.sunke.websocket.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import vip.sunke.websocket.listener.RequestListener;

import javax.annotation.Resource;
import java.net.UnknownHostException;

/**
 * @author sunke
 * @Date 2019-05-20 14:13
 * @description
 */
@Configuration
public class WebSocketConfig {


    @Resource
    private RequestListener requestListener;

    @Resource
    RedisConnectionFactory redisConnectionFactory;


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    @Bean
    public ServletListenerRegistrationBean<RequestListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<RequestListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(requestListener);
        return servletListenerRegistrationBean;
    }








    @Bean(value = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() throws UnknownHostException {

        StringRedisTemplate template = new StringRedisTemplate();

        template.setConnectionFactory(redisConnectionFactory);

        return template;

    }
}
