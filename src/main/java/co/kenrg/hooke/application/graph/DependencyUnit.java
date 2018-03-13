package co.kenrg.hooke.application.graph;

import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

public class DependencyUnit {
    public final String name;
    public final Class providesClass;
    public final List<Pair<String, Class>> dependencies;
    private final Supplier<Object> instanceProvider;

    public DependencyUnit(String name, Class providesClass, List<Pair<String, Class>> dependencies, Supplier<Object> provider) {
        this.name = name;
        this.providesClass = providesClass;
        this.dependencies = dependencies;
        this.instanceProvider = provider;
    }

    public Object getInstance() {
        return instanceProvider.get();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
