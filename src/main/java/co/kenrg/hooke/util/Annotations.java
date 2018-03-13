package co.kenrg.hooke.util;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

public class Annotations {
    public static Annotations getAnnotations(Supplier<Annotation[]> annotationsSupplier) {
        Annotations annotations = new Annotations();
        for (Annotation annotation : annotationsSupplier.get()) {
            annotations.put(annotation.annotationType(), annotation);
        }
        return annotations;
    }

    private Map<Class, Annotation> map = Maps.newHashMap();

    private void put(Class c, Annotation a) {
        this.map.put(c, a);
    }

    public boolean contains(Class c) {
        return this.map.containsKey(c);
    }

    public void ifContains(Class c, Runnable fn) {
        if (this.contains(c)) {
            fn.run();
        }
    }

    public <T> T get(Class<T> clazz) {
        return (T) this.map.get(clazz);
    }
}

