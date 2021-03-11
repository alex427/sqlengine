package alex.learn.sqlexecute.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * author  : zhiguang
 * date    : 2018/7/6
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public ApplicationContextProvider() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationContextProvider.applicationContext == null){
            ApplicationContextProvider.applicationContext  = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static Object getBeanByBeanname(String name){
        return getApplicationContext().getBean(name);
    }


    public static <T> T getBeanByBeanClass(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }


    public static <T> T getBeanByNameAndClass(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}
