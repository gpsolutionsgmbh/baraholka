package com.kramar.data.profiling;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ProfilingBeanPostProcessor implements BeanPostProcessor {

    @Value("${profiling.enabled:false}")
    private boolean enableProfiling;
    private Map<String, Class> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null && enableProfiling) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (o, method, objects) -> {
                long before = System.nanoTime();
                Object retVal = method.invoke(bean, objects);
                long after = System.nanoTime();
                System.out.println("Begin profiling:");
                System.out.println("BeanName: " + beanName);
                long elapsedTime = after - before;
                System.out.println("Elapsed time in nano seconds: " + elapsedTime);
                System.out.println("Elapsed time in seconds: " + TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
                return retVal;
            });
        }
        return bean;
    }
}
