package vip.sunke.template.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author sunke
 * @Date 2018/8/16 10:32
 * @description
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {





    @Value("${file.upload.root}")
    private String filePath;
    @Value("${debug}")
    private boolean debug;


    @Override
    public void addCorsMappings(CorsRegistry registry) {


        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
                .allowedHeaders("X-Requested-with", "Content-Type", "authorization", "jsonType", "json-type")
                .exposedHeaders("Authorization", "X-Requested-With", "Content-Type")
                .allowCredentials(false).maxAge(3600);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/frame/**").addResourceLocations("classpath:/static/frame/");
        registry.addResourceHandler("/style/**").addResourceLocations("classpath:/static/style/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");

        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/files/**").addResourceLocations("file:///" + filePath + File.separator);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {




    }
}
