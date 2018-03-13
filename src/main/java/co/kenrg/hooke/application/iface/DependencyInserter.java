package co.kenrg.hooke.application.iface;

@FunctionalInterface
public interface DependencyInserter {
    void insert(Class clazz, Object instance);
}
