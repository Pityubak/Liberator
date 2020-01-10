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
package com.pityubak.liberator.service;

import com.pityubak.liberator.data.StoredData;
import com.pityubak.liberator.config.MethodDetails;
import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.exceptions.InjectionException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import com.pityubak.liberator.config.DependencyConfig;
import com.pityubak.liberator.data.ObserverService;
import com.pityubak.liberator.lifecycle.InstanceService;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 *
 * @author Pityubak
 * @since 2019.09.20
 * @version 1.0
 */
public class FieldInjectService {

    private final InstanceService instanceService;
    private final ObserverService observer;
    private final DependencyConfig config;

    public FieldInjectService(InstanceService creator, ObserverService observer, DependencyConfig config) {
        this.instanceService = creator;
        this.observer = observer;
        this.config = config;
    }

    /**
     * Invoke method and set target field if method's type is void, field
     * modification
     *
     * @param f, type:Field
     * @param obj, type:Object
     * @param list, type:MethodDetails collection
     * @throws IllegalArgumentException when method' returntype and type of
     * field are different.
     * @throws InjectionException when injection failed
     * 
     */
    protected void injectMethod(Field f, Object obj, List<Class<?>> list) {

        //Iterate annotationlist
        for (Class<?> anno : list) {
            Class<? extends Annotation> type = anno.asSubclass(Annotation.class);
            if (f.isAnnotationPresent(type)) {
                try {
                    //Make new instance of MethodDetails class through DependencyConfig
                    MethodDetails detail = this.config.methodMapping(anno);

                    //From stored data in MethodDetails
                    Class<?> cl = detail.getClassName();
                    Object instance = this.instanceService.createInstance(cl);
                    Class<?>[] params = new Class<?>[detail.getParams().size()];
                    params = detail.getParams().toArray(params);
                    Method method = cl.getDeclaredMethod(detail.getMethodName(),
                            params);

                    AccessController.doPrivileged((PrivilegedAction) () -> {
                        f.setAccessible(true);
                        return null;
                    });

                    Object field = f.getType().isInterface() ? f.getType() : f.get(obj);

                    Flag flagBase = new Flag.Builder(f.getAnnotation(type))
                            .withRequest(new Request(this.instanceService))
                            .withObserver(observer)
                            .withName(f.getName())
                            .withValue(field)
                            .build();

                    Process processor = new FlagProcess(flagBase);

                    Object[] args = processor.process(detail.getMethodFlag());
                    finalizeMethodInvocation(method, instance, args, f, obj);
                    //Field name and value stored in ObserverService
                    StoredData data = new StoredData(f.getType(), f.get(obj));
                    observer.registrationWithStoredData(f.getName(), data);
                } catch (NoSuchMethodException | InstantiationException
                        | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException ex) {
                    throw new InjectionException("Injection failed : " + f.getName() + " : " + ex);
                }
            }

        }

    }

    private void finalizeMethodInvocation(Method method, Object instance, Object[] args, Field f, Object obj)
            throws  IllegalAccessException, InvocationTargetException {
        if (method.getReturnType().equals(Void.TYPE)) {
            method.invoke(instance, args);
            
        } else {
            Object param = method.invoke(instance, args);
            if (method.getReturnType().equals(f.getType()) || f.getType().isAssignableFrom(param.getClass())) {
                f.set(obj, param);
                //f.setAccessible(false);
                
            } else {
                throw new IllegalArgumentException("Return type mismatching: " + f.getType()
                        + " not equals or not assignable from" + param.getClass());
            }
        }
    }

}
