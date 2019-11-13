package vip.sunke.template.config;


import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * @author sunke
 * @Date 2018/8/3 16:49
 * @description
 */
@Configuration
public class DoZerMapperConfig {

   /* @Bean("skDozerMapper")
    public DozerBeanMapper dozerBeanMapperFactoryBean(@Value("${sk.dozer.path}") String resources) throws Exception {

        SkDozerBeanMapper dozerBeanMapper = new SkDozerBeanMapper();
        dozerBeanMapper.setMapperFilePath(resources);

        return dozerBeanMapper;
    }*/

    @Bean("skDozerMapper")
    public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(@Value("${sk.dozer.path}") Resource[] resources) throws Exception {
        final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
        dozerBeanMapperFactoryBean.setMappingFiles(resources);
        return dozerBeanMapperFactoryBean;
    }
}
