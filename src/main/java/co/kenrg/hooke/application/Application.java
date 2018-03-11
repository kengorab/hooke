package co.kenrg.hooke.application;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

import co.kenrg.hooke.annotations.Component;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class Application {
    private final Class appClass;
    private final Set<Class> componentClasses = Sets.newHashSet();

    public Application(Class appClass) {
        this.appClass = appClass;
    }

    public void run() throws IOException {
        String packageName = this.appClass.getPackage().getName();
        ClassLoader classLoader = this.appClass.getClassLoader();
        ImmutableSet<ClassInfo> allClasses = ClassPath.from(classLoader).getTopLevelClassesRecursive(packageName);
        for (ClassPath.ClassInfo classInfo : allClasses) {
            Class<?> clazz = classInfo.load();

            for (Annotation annotation : clazz.getAnnotations()) {
                if (annotation.annotationType().equals(Component.class)) {
                    componentClasses.add(clazz);
                }
            }
        }

        System.out.println(componentClasses);
    }
}
