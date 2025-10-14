package com.aleksej.minispring;

import java.lang.reflect.*;
import java.util.*;

public class MiniApplicationContext {

    private final Map<Class<?>, Object> singletonBeans = new HashMap<>();


    public MiniApplicationContext(Class<?>[] componentClasses) {
        System.out.println("--- MiniSpring Context Starting Up (Lifecycle Enabled) ---");

        System.out.println("Instantiating Raw Beans:");
        for (Class<?> componentClass : componentClasses) {

            if (componentClass.isAnnotationPresent(Component.class)) {
                Object instance = createBeanInstance(componentClass);
                singletonBeans.put(componentClass, instance);
                System.out.println("  --- Created raw instance: " + componentClass.getSimpleName());
            }
        }


        System.out.println(" Injecting Dependencies & Calling Lifecycle Hooks:");
        for (Object beanInstance : singletonBeans.values()) {
            injectDependencies(beanInstance);

            callLifecycleHook(beanInstance);
        }

        System.out.println("--- MiniSpring Context Ready ---");
    }


    private Object createBeanInstance(Class<?> beanClass) {
        try {
            return beanClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating bean instance for " + beanClass.getName(), e);
        }
    }

    private void injectDependencies(Object beanInstance) {
        Class<?> beanClass = beanInstance.getClass();

        for (Field field : beanClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Class<?> dependencyType = field.getType();

                Object dependency = getBean(dependencyType);

                if (dependency == null) {
                    throw new RuntimeException("Dependency not found for type: " + dependencyType.getName());
                }

                try {
                    field.setAccessible(true);
                    field.set(beanInstance, dependency);
                    System.out.println("  -> Injected " + dependencyType.getSimpleName() + " into " + beanClass.getSimpleName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error injecting dependency into " + beanClass.getName(), e);
                }
            }
        }
    }

    private void callLifecycleHook(Object beanInstance) {

        if (beanInstance instanceof InitializingBean) {
            System.out.println("  --Calling afterPropertiesSet() on " + beanInstance.getClass().getSimpleName());
            ((InitializingBean) beanInstance).afterPropertiesSet();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        T bean = (T) singletonBeans.get(type);
        if (bean == null) {
            throw new RuntimeException("Singleton bean not found for type: " + type.getName());
        }
        return bean;
    }
}
