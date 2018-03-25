package co.kenrg.hooke.util;

import static co.kenrg.hooke.util.Annotations.getAnnotations;

import java.lang.reflect.Parameter;
import java.util.List;

import co.kenrg.hooke.annotations.Qualifier;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

public class Parameters {
    public static List<Pair<String, Class>> getDependenciesFromParameters(Parameter[] parameters) {
        List<Pair<String, Class>> dependencies = Lists.newArrayList();
        for (Parameter parameter : parameters) {
            Annotations annotations = getAnnotations(parameter::getAnnotations);
            Qualifier qAnn = annotations.get(Qualifier.class);
            String name = (qAnn != null && !qAnn.value().isEmpty())
                ? qAnn.value()
                : null;
            dependencies.add(Pair.of(name, parameter.getType()));
        }
        return dependencies;
    }
}
