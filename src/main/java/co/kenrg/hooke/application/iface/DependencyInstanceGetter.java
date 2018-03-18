package co.kenrg.hooke.application.iface;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

@FunctionalInterface
public interface DependencyInstanceGetter {
    List<Object> getDependencyInstances(List<Pair<String, Class>> dependencies);
}
