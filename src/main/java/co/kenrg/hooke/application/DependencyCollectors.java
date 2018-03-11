package co.kenrg.hooke.application;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import co.kenrg.hooke.annotations.Autowired;
import co.kenrg.hooke.application.graph.DependencyUnit;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

public class DependencyCollectors {

    public static DependencyUnit getDependencyUnitForComponent(Class componentClass) {
        List<Pair<String, Class>> dependencies = Lists.newArrayList();

        Map<Field, Pair<String, Class>> fields = Maps.newHashMap();
        for (Field field : componentClass.getDeclaredFields()) {
            Autowired autowiredAnn = null;

            for (Annotation annotation : field.getAnnotations()) {
                if (annotation.annotationType().equals(Autowired.class)) {
                    autowiredAnn = (Autowired) annotation;
                    break;
                }
            }

            if (autowiredAnn != null) {
                Class<?> fieldType = field.getType();
                String fieldName = field.getName();

                Pair<String, Class> depPair = Pair.of(fieldName, fieldType);
                fields.put(field, depPair);
            }
        }

        dependencies.addAll(fields.values());

        return new DependencyUnit(componentClass.getName(), componentClass, dependencies);
    }
}
