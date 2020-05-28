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

import com.pityubak.liberator.builder.AnnotationCollection;
import com.pityubak.liberator.builder.ClassInstanceCollection;
import com.pityubak.liberator.builder.PreProcessingService;

import com.pityubak.liberator.builder.StartupData;
import com.pityubak.liberator.config.MethodDependencyService;

import com.pityubak.liberator.lifecycle.SingletonInstanceService;
import java.util.List;
import com.pityubak.liberator.builder.Data;
import com.pityubak.liberator.builder.InstanceCollection;
import com.pityubak.liberator.builder.PreProcessing;
import com.pityubak.liberator.config.ConfigResolverService;
import com.pityubak.liberator.config.Dependency;
import com.pityubak.liberator.config.ConfigDependencyService;
import com.pityubak.liberator.config.MainResolverService;
import com.pityubak.liberator.config.ParameterInterceptionHandling;
import com.pityubak.liberator.data.Interception;
import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.lifecycle.InstanceService;
import com.pityubak.liberator.service.AbstractMethodHandling;
import com.pityubak.liberator.service.DetailsService;
import com.pityubak.liberator.service.MethodDetailsService;

import java.util.HashMap;
import java.util.Map;
import com.pityubak.liberator.config.MethodDependency;
import com.pityubak.liberator.config.MethodResolverService;
import com.pityubak.liberator.config.Resolver;
import com.pityubak.liberator.layer.CollectionConfigurationLayer;

/**
 *
 * @author Pityubak
 * @since 2020.05.26
 * @version 1.2
 */
public final class Liberator {

    private final InstanceService instanceService;
    private final Container container;
    private final InstanceCollection classCollection;
    private final MethodDependency methodDependency;

    private final Dependency configDependency;
    private final Data startupData;
    private final Resolver configResolving;
    private final Resolver methodResolving;
    private final Resolver mainResolver;
    private final DetailsService detailsService;
    private final AbstractMethodHandling methodHandling;

    private final ParameterInterceptionHandling fieldHandling;

    private final PreProcessing collection;

    private final PreProcessing preProcessor;

    public Liberator(final Class<?> cl) {
        this.fieldHandling = new ParameterInterceptionHandling();
        this.instanceService = new SingletonInstanceService(this.fieldHandling);
        this.startupData = new StartupData(cl.getResource(cl.getSimpleName() + ".class").getPath());
        this.configDependency = new ConfigDependencyService();

        this.classCollection = new ClassInstanceCollection(this.startupData, cl);
        this.preProcessor = new PreProcessingService(this.configDependency, this.classCollection);
        this.methodDependency = new MethodDependencyService();

        this.methodHandling = new AbstractMethodHandling(this.instanceService);
        this.configResolving = new ConfigResolverService(this.configDependency, 
                (CollectionConfigurationLayer) this.classCollection, this.instanceService, this.methodHandling);
        this.methodResolving = new MethodResolverService(this.methodDependency);
        this.mainResolver = new MainResolverService(this.configResolving, this.methodResolving);
        this.detailsService = new MethodDetailsService(this.methodDependency, this.instanceService);
        this.collection = new AnnotationCollection(this.methodDependency, this.classCollection);
        this.container = new Container(this.instanceService, this.methodDependency, this.detailsService, this.methodHandling);
        this.preProcessor.collect();

    }

    /**
     * Require to proper running of Liberator.Always it's neccesary to call
     * before inject.
     *
     * @param cl
     */
    public void init(final Class<?> cl) {
        this.mainResolver.resolve(cl);
        this.collection.collect();
    }



    /**
     * Interception registration
     *
     * @param interception
     *
     */
    public void registerInterception(final Interception interception) {
        this.fieldHandling.registrate(interception);
    }

    /**
     *
     * @param mainClass
     * @return list of all classes in package of mainClass
     */
    public List<Class<?>> getClassListFromTargetPackage(final Class<?> mainClass) {

        final Data data = new StartupData(mainClass.getResource(mainClass.getSimpleName() + ".class").getPath());
        final ClassInstanceCollection clsCollector = new ClassInstanceCollection(data, mainClass);

        return clsCollector.collect();
    }

    /**
     *
     * @return new Request
     */
    public Request askForRequest() {
        return new Request(this.instanceService);
    }

    /**
     *
     * @param injectedList
     */
    public void inject(final List<Object> injectedList) {
        this.container.inject(injectedList);
    }

    /**
     *
     * @param map
     */
    public void inject(final Map<String, Class<?>> map) {
        this.container.inject(map);
    }

    /**
     *
     * @param classes
     */
    public void inject(final Class<?> ...classes) {
        final Map<String, Class<?>> map = new HashMap<>();
        for (Class<?> cl : classes) {
            map.put(cl.getSimpleName(), cl);

        }
        this.container.inject(map);
    }

}
