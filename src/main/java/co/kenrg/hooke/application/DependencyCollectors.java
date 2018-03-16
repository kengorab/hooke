package co.kenrg.hooke.application;

import static co.kenrg.hooke.util.Annotations.getAnnotations;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import co.kenrg.hooke.annotations.Autowired;
import co.kenrg.hooke.annotations.Bean;
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

    public static List<DependencyUnit> getDependencyUnitsForBeanMethods(Set<Class> configClasses, DependencyInstanceGetter dependencyInstanceGetter) {
        List<DependencyUnit> units = Lists.newArrayList();
        for (Class configClass : configClasses) {
            final Object instance = instantiateClass(configClass);

            for (Method method : configClass.getMethods()) {
                boolean hasBean = getAnnotations(method::getAnnotations).contains(Bean.class);

                if (hasBean) {
                    if (method.getReturnType() == Void.TYPE) {
                        throw new IllegalStateException("Method " + method + " given @Bean annotation, but returns void!");
                    }

                    final List<Pair<String, Class>> dependencies = getDependenciesFromParameters(method.getParameters());
                    List<Class> dependencyClasses = dependencies.stream().map(Pair::getRight).collect(toList());

                    DependencyUnit dependencyUnit = new DependencyUnit(
                        method.getName(),
                        method.getReturnType(),
                        dependencies,
                        () -> {
                            try {
                                List<Object> args = dependencyInstanceGetter.getDependencyInstances(dependencyClasses);
                                return method.invoke(instance, args.toArray());
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new IllegalStateException("Could not invoke method " + method, e);
                            }
                        }
                    );
                    units.add(dependencyUnit);
                }
            }
        }
        return units;
    }

    private static Object instantiateClass(Class configClass) {
        for (Constructor constructor : configClass.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                try {
                    return constructor.newInstance();
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new IllegalStateException("Could not invoke constructor " + constructor, e);
                }
            }
        }

        throw new IllegalStateException("No default no-args constructor for class: " + configClass);
    }

    private static List<Pair<String, Class>> getDependenciesFromParameters(Parameter[] parameters) {
        List<Pair<String, Class>> dependencies = Lists.newArrayList();
        for (Parameter parameter : parameters) {
            dependencies.add(Pair.of(null, parameter.getType()));
        }
        return dependencies;
    }
}
