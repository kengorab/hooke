package co.kenrg.hooke.application;

import static co.kenrg.hooke.util.Annotations.getAnnotations;
import static co.kenrg.hooke.util.Parameters.getDependenciesFromParameters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import co.kenrg.hooke.annotations.Autowired;
import co.kenrg.hooke.application.iface.DependencyInstanceGetter;
import org.apache.commons.lang3.tuple.Pair;

public class DependencyPostSetupHandlers {

    public static void invokeAutowiredMethodsForBeans(List<Object> beanInstances, DependencyInstanceGetter dependencyInstanceGetter) {
        for (Object instance : beanInstances) {
            for (Method method : instance.getClass().getMethods()) {
                boolean hasAutowired = getAnnotations(method::getAnnotations).contains(Autowired.class);

                if (hasAutowired) {
                    final List<Pair<String, Class>> dependencies = getDependenciesFromParameters(method.getParameters());
                    List<Object> args = dependencyInstanceGetter.getDependencyInstances(dependencies);
                    try {
                        method.invoke(instance, args.toArray());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new IllegalStateException("Could not invoke @Autowired method " + method, e);
                    }
                }
            }
        }
    }
}
