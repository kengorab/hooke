package co.kenrg.hooke.application;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

public class ApplicationContext {
    private final Map<Class, Object> allBeans = Maps.newHashMap();
    private final Map<String, Object> namedBeans = Maps.newHashMap();

    public void insert(Class clazz, @Nullable String name, Object instance) {
        allBeans.put(clazz, instance);
        if (name != null) {
            namedBeans.put(name, instance);
        }
    }

    public List<Object> getBeans(Collection<Pair<String, Class>> beans) {
        List<Object> values = Lists.newArrayList();
        for (Pair<String, Class> pair : beans) {
            Object instance;
            String errMsg;
            if (pair.getLeft() == null) {
                instance = allBeans.get(pair.getRight());
                errMsg = "No bean available of type " + pair.getRight();
            } else {
                instance = namedBeans.get(pair.getLeft());
                errMsg = "No bean available for name " + pair.getLeft();
            }

            if (instance == null) {
                throw new IllegalStateException(errMsg);
            }

            values.add(instance);
        }

        return values;
    }

    public List<Object> getBeansOfTypes(Collection<Class> types) {
        List<Object> values = Lists.newArrayList();
        for (Class type : types) {
            Object instance = allBeans.get(type);
            if (instance == null) {
                throw new IllegalStateException("No bean available of type " + type);
            }
            values.add(instance);
        }

        return values;
    }

    public <T> T getBeanOfType(Class<T> clazz) {
        return (T) allBeans.get(clazz);
    }
}
