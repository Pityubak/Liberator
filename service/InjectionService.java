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

import com.pityubak.liberator.exceptions.InjectionException;
import com.pityubak.liberator.misc.ModificationFlag;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import com.pityubak.liberator.config.DependencyConfig;
import com.pityubak.liberator.data.ObserverService;
import com.pityubak.liberator.exceptions.ClassInstantiationException;
import com.pityubak.liberator.lifecycle.InstanceService;

/**
 *
 * @author Pityubak
 * @since 2019.09.20
 * @version 1.0
 * @see FieldInjectService
 * @see Injection
 */
public class InjectionService implements Injection {

    private final MarkedClassInspect inspector;
    private final FieldInjectService fieldService;
    private final InstanceService instanceService;
    private final DependencyConfig config;
    private final ObserverService observer;

    public InjectionService(ObserverService observer, InstanceService instanceService, DependencyConfig config) {
        this.observer = observer;
        this.instanceService = instanceService;
        this.config = config;
        this.inspector = new MarkedClassInspectImpl(this.instanceService, this.observer, this.config);
        this.fieldService = new FieldInjectService(this.instanceService, this.observer, this.config);
    }

    /**
     *
     * @param injectedClass, Type:wildcard class
     * @param flag
     * @throws InjectionException when injection failed
     * @throws ClassInstantiationException when return null value
     */
    @Override
    public void inject(Class<?> injectedClass, ModificationFlag flag) {

        //New instance creating 
        if (!injectedClass.isInterface()) {
            try {
                Object obj = this.instanceService.createInstance(injectedClass);

                if (injectedClass.getDeclaredAnnotations().length > 0) {
                    this.inspector.getMarkedClass(injectedClass, this.config.getAnnotationList(flag));
                }
                //It iterate over declared fields of injected class
                //and call parentclass method
                for (Field f : injectedClass.getDeclaredFields()) {

                    if (f.getDeclaredAnnotations().length > 0) {
                        this.fieldService.injectMethod(f, obj, this.config.getAnnotationList(flag));
                    }

                }

            } catch (NoSuchMethodException | InstantiationException
                    | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException ex) {
                throw new InjectionException("Injection failed : " + injectedClass.getName() + " : " + ex);
            }
        }

    }

}
