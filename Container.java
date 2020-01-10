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
import com.pityubak.liberator.builder.InstanceCollection;
import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.service.InjectionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.pityubak.liberator.config.DependencyConfig;
import com.pityubak.liberator.data.ObserverService;
import com.pityubak.liberator.lifecycle.InstanceService;

/**
 * Container handles all operation instance creation method invocation
 *
 * @author Pityubak
 * @since 2019.09.20
 * @version 1.0
 */
public class Container {

    private final AnnotationCollection collection;
    private final InstanceService instanceService;
    private final ObserverService observerService;
    private final DependencyConfig config;
    private final InstanceCollection instanceCollection;
    private final InjectionService injectionService;

    public Container(DependencyConfig config, InstanceService creator,
            ObserverService observer, InstanceCollection instanceCollection) {

        this.instanceService = creator;
        this.observerService = observer;
        this.config = config;
        this.instanceCollection = instanceCollection;
        this.collection = new AnnotationCollection(this.config, this.instanceCollection);
        this.collection.collectAnnotation();
        this.injectionService = new InjectionService(this.observerService, this.instanceService, this.config);
    }

    /**
     *
     * @param injectedClasses
     *
     */
    public void inject(Class<?>[] injectedClasses) {

        // Phase 1:Creation
        //
        ExecutorService executor = Executors.newSingleThreadExecutor();
        //Creation is very important
        Future<Void> creation = executor.submit(() -> {
            for (Class<?> injectedClass : injectedClasses) {

                injectionService.inject(injectedClass, ModificationFlag.PRIORITY_CREATION);
            }
            return null;
        });
        //wait until creation phase done
        try {
            creation.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Container.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }
        //Phase 2: High

        Future<Void> highPriority = executor.submit(() -> {
            for (Class<?> injectedClass : injectedClasses) {

                this.injectionService.inject(injectedClass, ModificationFlag.PRIORITY_HIGH);

            }
            return null;
        });
        //wait until creation phase done
        try {
            highPriority.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Container.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }

        Future<Void> normalPriority = executor.submit(() -> {
            //Phase 3:Normal

            for (Class<?> injectedClass : injectedClasses) {

                this.injectionService.inject(injectedClass, ModificationFlag.PRIORITY_NORMAL);

            }
            return null;
        });
        //wait until creation phase done
        try {
            normalPriority.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Container.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }

        Future<Void> lowPriority = executor.submit(() -> {
            //Phase 4:Low
            for (Class<?> injectedClass : injectedClasses) {

                this.injectionService.inject(injectedClass, ModificationFlag.PRIORITY_LOW);

            }
            return null;
        });
        //wait until creation phase done
        try {
            lowPriority.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Container.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }

        executor.shutdown();
    }

}
