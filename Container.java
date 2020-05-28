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

import com.pityubak.liberator.proxy.InjectConsumer;
import com.pityubak.liberator.service.InjectionService;
import com.pityubak.liberator.lifecycle.InstanceService;
import com.pityubak.liberator.proxy.InjectionProxy;
import com.pityubak.liberator.proxy.InjectionStream;
import com.pityubak.liberator.service.AbstractMethodHandling;
import com.pityubak.liberator.service.DetailsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.pityubak.liberator.config.MethodDependency;

/**
 * Container handles all operation
 *
 * @author Pityubak
 * @since 2020.02.24
 * @version 1.1
 */
public class Container {

    private final InstanceService instanceService;
    private final MethodDependency config;
    private final InjectionService injectionService;
    private final AbstractMethodHandling methodHandling;
    private final DetailsService detailsService;

    public Container(InstanceService instanceService, MethodDependency config,
            DetailsService detailsService, AbstractMethodHandling methodHandling) {
        this.instanceService = instanceService;
        this.config = config;
        this.detailsService = detailsService;
        this.methodHandling = methodHandling;
        this.injectionService = new InjectionService(this.instanceService, this.config, this.detailsService, this.methodHandling);
    }

    /**
     *
     * @param injectedClasses
     *
     */
    public void inject(final Map<String, Class<?>> injectedClasses) {

        final List<Object> namedObjectList = new ArrayList<>();
        injectedClasses.keySet().forEach(name -> {
            namedObjectList.add(this.instanceService.createInstance(name, injectedClasses.get(name)));
        });
        //
        this.inject(namedObjectList);
    }

    public void inject(final List<Object> injectedClass) {

        final Consumer consumer = new InjectConsumer(injectedClass, this.injectionService);
        final InjectionStream injectionStream = new InjectionProxy(consumer, this.methodHandling);
        injectionStream.execute();

    }

}
