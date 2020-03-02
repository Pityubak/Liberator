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

import com.pityubak.liberator.builder.StartupData;
import com.pityubak.liberator.config.MethodDependencyConfig;

import com.pityubak.liberator.lifecycle.SingletonInstanceService;
import java.util.List;
import com.pityubak.liberator.builder.Data;
import com.pityubak.liberator.builder.InstanceCollection;
import com.pityubak.liberator.config.DependencyConfig;
import com.pityubak.liberator.config.ParameterInterceptionHandling;
import com.pityubak.liberator.data.Interception;
import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.lifecycle.InstanceService;
import com.pityubak.liberator.misc.Insertion;
import com.pityubak.liberator.service.AbstractMethodHandling;
import com.pityubak.liberator.service.DetailsService;
import com.pityubak.liberator.service.MethodDetailsService;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Pityubak
 * @since 2020.02.20
 * @version 1.1
 */
public final class Liberator {

    private final InstanceService instanceService;
    private final Container container;
    private final InstanceCollection classCollection;
    private final DependencyConfig config;
    private final Data startupData;

    private final DetailsService detailsService;
    private final AbstractMethodHandling methodHandling;

    private final ParameterInterceptionHandling fieldHandling;

    private final AnnotationCollection collection;

    public Liberator(final Class<?> cl) {
        this.fieldHandling = new ParameterInterceptionHandling();
        this.instanceService = new SingletonInstanceService(this.fieldHandling);
        this.startupData = new StartupData(cl.getResource(cl.getSimpleName() + ".class").getPath());

        this.classCollection = new ClassInstanceCollection(this.startupData, cl);
        this.config = new MethodDependencyConfig();
        this.methodHandling = new AbstractMethodHandling(this.instanceService);
        this.detailsService = new MethodDetailsService(this.config, this.instanceService);
        this.collection = new AnnotationCollection(this.config, this.classCollection);
        this.container = new Container(this.instanceService, this.config, this.detailsService, this.methodHandling);

    }

    /**
     * Require to proper running of Liberator.Always it's neccesary to call
     * before inject.
     */
    public void init() {

        this.collection.collectAnnotation();
    }

    /**
     * Reset Liberator's setting.
     */
    public void reset() {
        this.classCollection.removeAll();
        this.config.removeMapping();
        this.methodHandling.removeAll();
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
     * @param cl ,which Liberator should ignore.
     */
    public void addFilter(final Class<?> cl) {
        this.classCollection.registerFilterClass(cl);
    }

    /**
     *
     * @param parent- target class
     * @param cl--single method interface/abtract class
     * @param insertion -Insertion, it shows that when it inject
     */
    public void registerAbstractMethod(final Class<?> parent, final Class<?> cl, final Insertion insertion) {
        this.methodHandling.registrate(parent, cl, insertion);
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
    public void inject(final Class<?>[] classes) {
        final Map<String, Class<?>> map = new HashMap<>();
        for (Class<?> cl : classes) {
            map.put(cl.getSimpleName(), cl);

        }
        this.container.inject(map);
    }

}
