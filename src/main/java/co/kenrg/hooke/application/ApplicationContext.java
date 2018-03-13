package co.kenrg.hooke.application;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ApplicationContext {
    private final Map<Class, Object> applicationContext = Maps.newHashMap();

    public void insert(Class clazz, Object instance) {
        applicationContext.put(clazz, instance);
    }

    public List<Object> getBeansOfTypes(List<Class> classes) {
        List<Object> beans = Lists.newArrayList();
        for (Class clazz : classes) {
            if (applicationContext.containsKey(clazz)) {
                beans.add(applicationContext.get(clazz));
            } else {
                throw new IllegalStateException("No bean available of type " + clazz);
            }
        }
        return beans;
    }

    public <T> T getBeanOfType(Class<T> clazz) {
        return (T) getBeansOfTypes(Lists.newArrayList(clazz)).get(0);
    }
}
