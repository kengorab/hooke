package co.kenrg.hooke.application.graph;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

public class DependencyUnit {
    public final String name;
    public final Class providesClass;
    public final List<Pair<String, Class>> dependencies;

    public DependencyUnit(String name, Class providesClass, List<Pair<String, Class>> dependencies) {
        this.name = name;
        this.providesClass = providesClass;
        this.dependencies = dependencies;
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
