/*
 * The MIT License
 *
 * Copyright 2020 Pityubak.
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

import com.pityubak.liberator.annotations.Config;
import com.pityubak.liberator.config.ConfigDetails;
import com.pityubak.liberator.config.ConfigurationService;
import com.pityubak.liberator.config.Dependency;

/**
 *
 * @author Pityubak
 * @since 2020.05.12
 * @version 1.0
 *
 */
public class PreProcessingService implements PreProcessing {

    private final Dependency dependency;
    private final InstanceCollection collection;

    public PreProcessingService(Dependency configDependency, InstanceCollection collection) {
        this.dependency = configDependency;
        this.collection = collection;
    }

    @Override
    public void collect() {
        this.collection.collect().forEach(this::registerConfigObject);
    }

    private void registerConfigObject(final Class<?> loadedClass) {
        if (loadedClass.isAnnotationPresent(Config.class)) {
            this.createConfigDetails(loadedClass);
        } 
    }

    private void createConfigDetails(final Class<?> loadedClass) {
        final Config config = loadedClass.getAnnotation(Config.class);
        if (!loadedClass.getInterfaces()[0].equals(ConfigurationService.class)) {
            throw new IllegalArgumentException("Config class:" + loadedClass.getSimpleName()
                    + "must implement ConfigurationService interface ");
        }
        ConfigDetails details = new ConfigDetails(config.value(), loadedClass);
        this.dependency.createMapping(details);
    }



}
