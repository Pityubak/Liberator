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

import com.pityubak.liberator.config.DependencyConfig;
import com.pityubak.liberator.config.MethodDetails;
import com.pityubak.liberator.lifecycle.InstanceService;
import com.pityubak.liberator.misc.ModificationFlag;
import java.lang.reflect.Method;

/**
 *
 * @author Pityubak
 */
public final class MethodDetailsService implements DetailsService {

    private final DependencyConfig config;

    private final InstanceService service;

    private MethodDetails detail;

    public MethodDetailsService(DependencyConfig config, InstanceService service) {
        this.config = config;
        this.service = service;
    }

    @Override
    public Object processInstance() {
        final Class<?> cls = detail.getClassName();

        return this.service.createInstance(cls.getSimpleName(), cls);
    }

    @Override
    public Method processMethod() throws NoSuchMethodException {
        final Class<?> cl = detail.getClassName();

        Class<?>[] params = new Class<?>[detail.getParams().size()];
        params = detail.getParams().toArray(params);

        return cl.getDeclaredMethod(detail.getMethodName(),
                params);
    }

    @Override
    public void processDetails(final Class<?> cl, final ModificationFlag flag) {
        this.detail = this.config.methodMapping(cl, flag);
    }

}
