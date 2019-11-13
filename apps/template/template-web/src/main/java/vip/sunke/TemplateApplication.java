package vip.sunke;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ServletComponentScan(basePackages = {"vip.sunke.template.filter"})
@MapperScan({"vip.sunke.template.dao.mapper","vip.sunke.template.dao.mapperExt"})

@PropertySource(value = "config.properties")
public class TemplateApplication {


    private static ApplicationContext applicationContext;


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        TemplateApplication.applicationContext = applicationContext;
    }


    public static void main(String[] args) {


        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(TemplateApplication.class, args);
        TemplateApplication.setApplicationContext(configurableApplicationContext);
        // WsPool.setApplicationContext(configurableApplicationContext);


    }


}
