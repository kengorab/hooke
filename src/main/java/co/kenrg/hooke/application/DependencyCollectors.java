package co.kenrg.hooke.application;

import static co.kenrg.hooke.util.Annotations.getAnnotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import co.kenrg.hooke.annotations.Autowired;
import co.kenrg.hooke.application.graph.DependencyUnit;
import co.kenrg.hooke.application.iface.DependencyInstanceGetter;
import co.kenrg.hooke.util.Annotations;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

public class DependencyCollectors {

    public static DependencyUnit getDependencyUnitForComponent(Class componentClass, DependencyInstanceGetter dependencyInstanceGetter) {
        List<Pair<String, Class>> dependencies = Lists.newArrayList();

        Map<Field, Pair<String, Class>> fields = Maps.newHashMap();
        for (Field field : componentClass.getDeclaredFields()) {
            Annotations annotations = getAnnotations(field::getAnnotations);
            Autowired autowiredAnn = annotations.get(Autowired.class);

            if (autowiredAnn != null) {
                Class<?> fieldType = field.getType();
                String fieldName = field.getName();

                Pair<String, Class> depPair = Pair.of(fieldName, fieldType);
                fields.put(field, depPair);
            }
        }

        dependencies.addAll(fields.values());

        return new DependencyUnit(
            componentClass.getName(),
            componentClass,
            dependencies,
            () -> {
                try {
                    Constructor constructor = componentClass.getConstructors()[0];
                    Object instance = constructor.newInstance();

                    for (Map.Entry<Field, Pair<String, Class>> entry : fields.entrySet()) {
                        Field field = entry.getKey();
                        Pair<String, Class> dependency = entry.getValue();
                        Class depType = dependency.getValue();

                        Object value = dependencyInstanceGetter.getDependencyInstances(Lists.newArrayList(depType)).get(0);
                        if (!field.isAccessible()) field.setAccessible(true);
                        field.set(instance, value);
                    }

                    return instance;
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    throw new IllegalStateException("Could not invoke constructor for class" + componentClass, e);
                }
            }
        );
    }
}
