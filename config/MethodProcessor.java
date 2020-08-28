package com.pityubak.liberator.config;

import com.pityubak.liberator.annotations.MethodBox;
import com.pityubak.liberator.annotations.MethodElement;
import com.pityubak.liberator.builder.Mapper;
import com.pityubak.liberator.builder.MethodDetails;
import java.lang.reflect.Method;

/**
 *
 * @author Pityubak
 */
public class MethodProcessor implements Processor {

    private final Mapper methodConfig;

    public MethodProcessor(Mapper methodConfig) {
        this.methodConfig = methodConfig;
    }

    @Override
    public void registerObject(final Class<?> loadedClass) {
        if (loadedClass.isAnnotationPresent(MethodBox.class)) {
            for (Method method : loadedClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(MethodElement.class)) {
                    if (!method.isBridge() && !method.isSynthetic()) {
                        this.createMethodDetails(method, loadedClass);
                    }
                }
            }
        }

    }

    private void createMethodDetails(final Method method, final Class<?> loadedClass) {
        final MethodElement element = method.getAnnotation(MethodElement.class);
        final Class<?>[] paramType = method.getParameterTypes();

        if (paramType.length == 0) {
            throw new IllegalArgumentException("Parameter is missing from injected method  "
                    + method
                    + " Injected method always need  target annotation on first place.");
        } else {

            MethodDetails details = new MethodDetails.Builder()
                    .addMethodName(method.getName())
                    .addClassName(loadedClass)
                    .withParams(paramType)
                    .withAnnotation(paramType[0])
                    .withModificationFlag(element.value())
                    .withSimpleName(loadedClass.getSimpleName())
                    .build();
            methodConfig.createMapping(details);

        }
    }
}
