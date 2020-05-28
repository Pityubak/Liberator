/*
 * The MIT License
 *
 * Copyright 2019 Pityubak.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.pityubak.liberator.builder;

import com.pityubak.liberator.annotations.MethodBox;
import com.pityubak.liberator.annotations.MethodElement;
import com.pityubak.liberator.config.MethodDetails;
import java.lang.reflect.Method;
import com.pityubak.liberator.config.MethodDependency;

/**
 *
 * @author Pityubak
 * @since 2020.02.12
 * @version 1.1 Make MethodDetails class and store it
 *
 */
public final class AnnotationCollection implements PreProcessing {

    private final MethodDependency methodConfig;

    private final InstanceCollection collection;

    public AnnotationCollection(MethodDependency methodConfig, InstanceCollection collection) {
        this.methodConfig = methodConfig;
        this.collection = collection;
    }

    @Override
    public void collect() {
        this.collection.collect().forEach(this::registerMethodObject);
    }

    private void registerMethodObject(final Class<?> loadedClass) {

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
            methodConfig.createMethodMapping(details);

        }
    }

}
