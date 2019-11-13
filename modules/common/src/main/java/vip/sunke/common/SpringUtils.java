package vip.sunke.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 * @author sunke
 * @Date 2017/6/9 14:13
 * @description
 */


@Component
public class SpringUtils implements BeanFactoryPostProcessor,
        BeanPostProcessor, ApplicationContextAware {


    private static ConfigurableApplicationContext applicationContext = null;

    private static ConfigurableListableBeanFactory beanFactory = null;

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringUtils.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    public static ConfigurableListableBeanFactory getConfigurableListableBeanFactory() {
        return beanFactory;
    }

    public static void registerBean(String resource) {
        BeanDefinitionRegistry beanDefinitionRegistry = ((BeanDefinitionRegistry) getConfigurableListableBeanFactory());

        XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(
                beanDefinitionRegistry);

        definitionReader.setResourceLoader(getApplicationContext());
        definitionReader.setEntityResolver(new ResourceEntityResolver(getApplicationContext()));

        definitionReader.setValidating(false);
        definitionReader
                .setValidationMode(XmlBeanDefinitionReader.VALIDATION_NONE);

        definitionReader.loadBeanDefinitions(new FileSystemResource(resource));
    }

    public static void unregisterBean(String beanName) {
        if (containsBean(beanName)) {
            BeanDefinitionRegistry beanDefinitionRegistry = ((BeanDefinitionRegistry) getConfigurableListableBeanFactory());
            beanDefinitionRegistry.removeBeanDefinition(beanName);
        }
    }

    public static boolean containsBean(String beanName) {
        return getConfigurableListableBeanFactory().containsBeanDefinition(
                beanName);
    }

    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}
