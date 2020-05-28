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
package com.pityubak.liberator.config;

import com.pityubak.liberator.builder.InstanceCollection;
import com.pityubak.liberator.lifecycle.InstanceService;
import com.pityubak.liberator.service.AbstractMethodHandling;
import com.pityubak.liberator.layer.CollectionConfigurationLayer;

/**
 *
 * @author Pityubak
 * @since 2020.05.22
 * @version 1.0
 *
 */
public class ConfigResolverService implements Resolver {

    private final Dependency config;
    private final CollectionConfigurationLayer collection;
    private final InstanceService instanceService;
    private final AbstractMethodHandling methodHandling;

    public ConfigResolverService(Dependency config, CollectionConfigurationLayer collection,
            InstanceService instanceService, AbstractMethodHandling methodHandling) {
        this.config = config;
        this.collection = collection;
        this.instanceService = instanceService;
        this.methodHandling = methodHandling;
    }

    @Override
    public void resolve(final Class<?> cl) {
        InstanceCollection colls = (InstanceCollection) this.collection;
        colls.removeAll();
        this.methodHandling.removeAll();
        final ConfigDetails details = (ConfigDetails) this.config.mapping(cl);
        final Class<?> service = details.getService();
        final ConfigurationService configService
                = (ConfigurationService) this.instanceService.createInstance(service.getSimpleName(), service);

        configService.filter(collection);
        configService.registerAbstractMethod(methodHandling);
    }

}
