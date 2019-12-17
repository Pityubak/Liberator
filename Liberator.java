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
package com.pityubak.liberator;

import com.pityubak.liberator.builder.ClassInstanceCollection;

import com.pityubak.liberator.builder.StartupData;
import com.pityubak.liberator.data.ObjectObserverService;
import com.pityubak.liberator.config.MethodDependencyConfig;

import com.pityubak.liberator.lifecycle.SingletonInstanceService;
import java.util.List;
import com.pityubak.liberator.builder.Data;
import com.pityubak.liberator.builder.InstanceCollection;
import com.pityubak.liberator.config.DependencyConfig;
import com.pityubak.liberator.data.ObserverService;
import com.pityubak.liberator.exceptions.ClassInstantiationException;
import com.pityubak.liberator.lifecycle.InstanceService;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Pityubak
 * @since 2019.09.20
 * @version 1.0
 */
public final class Liberator {

    private final InstanceService instanceService;
    private final Container container;
    private final InstanceCollection classCollection;
    private final DependencyConfig config;
    private final ObserverService observerService;
    private final Data startupData;

    public Liberator(final Class<?> cl) {
        this.instanceService = new SingletonInstanceService();
        this.startupData = new StartupData(cl.getResource(cl.getSimpleName() + ".class").getPath());

        classCollection = new ClassInstanceCollection(this.startupData, cl);
        this.config = new MethodDependencyConfig();
        this.observerService = new ObjectObserverService();
        this.container=new Container(this.config, this.instanceService, this.observerService, this.classCollection);
    }



    public List<Class<?>> getClassListFromTargetPackage(final Class<?> mainClass) {

        Data data = new StartupData(mainClass.getResource(mainClass.getSimpleName() + ".class").getPath());
        ClassInstanceCollection clsCollector = new ClassInstanceCollection(data, mainClass);

        return clsCollector.collect();
    }

    public Object inject(Class<?> injectedClass) {
        try {
            //Create new instance from injectedClass, use default, parameterless constructor
            return this.instanceService.createInstance(injectedClass);

        } catch (NoSuchMethodException | InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException ex) {
             throw new ClassInstantiationException("This class instance : " + injectedClass.getName() + " is null: "+ex);
        }

    }
    
    public void inject(Class<?>[] classes){
        this.container.injectVoidMethod(classes);
    }

}
