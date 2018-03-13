package co.kenrg.hooke.application.iface;

import java.util.List;

@FunctionalInterface
public interface DependencyInstanceGetter {
    List<Object> getDependencyInstances(List<Class> dependencies);
}
