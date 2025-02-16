package com.example.common;

import com.example.annotation.Autowired;
import com.example.annotation.Component;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BeanContext {
        private final Map<Class<?>, Object> beans = new HashMap<>();

        public BeanContext(String basePackage) throws Exception {
            // Scan and register components
            scanAndRegisterComponents(basePackage);
            // Inject dependencies
            injectDependencies();
        }

    private void scanAndRegisterComponents(String basePackage) throws Exception {
        String path = basePackage.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(path);

        if (resource == null) {
            throw new ClassNotFoundException("Package " + basePackage + " not found");
        }

        File directory = new File(resource.toURI());
        if (directory.exists()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.getName().endsWith(".class")) {
                    String className = basePackage + "." + file.getName().replace(".class", "");
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Component.class)) {
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        beans.put(clazz, instance);
                    }
                }
            }
        }
    }

        private void injectDependencies() throws IllegalAccessException {
            for (Object bean : beans.values()) {
                for (Field field : bean.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        field.setAccessible(true);
                        Object dependency = beans.get(field.getType());
                        if (dependency != null) {
                            field.set(bean, dependency);
                        }
                    }
                }
            }
        }

        public <T> T getBean(Class<T> type) {
            return type.cast(beans.get(type));
        }
}
