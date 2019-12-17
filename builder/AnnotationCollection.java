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
import com.pityubak.liberator.annotations.Register;
import com.pityubak.liberator.config.MethodDetails;
import com.pityubak.liberator.exceptions.ObjectNotRegisteredException;
import java.lang.reflect.Method;
import com.pityubak.liberator.config.DependencyConfig;

/**
 *
 * @author Pityubak
 * @since 2019.12.12
 * @version 1.0 Make MethodDetails class and store it
 *
 */
public final class AnnotationCollection {

    private final DependencyConfig methodConfig;

    private final InstanceCollection collection;

    public AnnotationCollection(final DependencyConfig methodConfig, final InstanceCollection collection) {
        this.methodConfig = methodConfig;
        this.collection = collection;
    }

    public void collectAnnotation() {
        this.collection.collect().forEach(this::registerMethodObject);
    }

    private void registerMethodObject(final Class<?> loadedClass) {

        /* If loadedClass is annotated with MethodBox it
          iterate over parameter's declared methods and check it method's
          annotationtype(MethodElement) present or not, and then collect data and
          make new instance of MethodDetails
         */
        if (loadedClass.isAnnotationPresent(MethodBox.class)) {
            for (Method method : loadedClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(MethodElement.class)) {
                    this.createMethodDetails(method, loadedClass);
                }
            }
        }
    }
    
    private void createMethodDetails( final Method method, final Class<?> loadedClass){
                   MethodElement element = method.getAnnotation(MethodElement.class);
                    Class<?>[] paramType = method.getParameterTypes();
                    if (paramType.length == 0) {
                        throw new IllegalArgumentException("Parameter is missing from injected method  "
                                + method
                                + " Injected method always need  otarget annotation on first place.");
                    } else {
                        if (!paramType[0].isAnnotationPresent(Register.class)) {
                            throw new ObjectNotRegisteredException("Parameter: "
                                    + paramType[0] + "is not annotation or annotation isn't registered");
                        } else {

                            MethodDetails details = new MethodDetails.Builder()
                                    .addMethodName(method.getName())
                                    .addClassName(loadedClass)
                                    .withParams(paramType)
                                    .withAnnotation(paramType[0])
                                    .withMethodFlag(element.value())
                                    .withModificationFlag(element.flag())
                                    .build();
                            methodConfig.createMethodMapping(details);

                        }
                    }
    }
}
