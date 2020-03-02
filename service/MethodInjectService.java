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

import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.proxy.InjectionPipe;
import com.pityubak.liberator.proxy.Pipe;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Pityubak Wrap source method in another
 */
public final class MethodInjectService implements ExecutorService<Method> {

    private final DetailsService service;

    public MethodInjectService(DetailsService service) {
        this.service = service;

    }

    @Override
    public void inject(final Method target, final Object obj, final List<Class<?>> list, final ModificationFlag flag) {

        final InjectFinalizerService injService = new MethodInjectFinalizerService(service, obj);
        final Pipe<Method> pipe = new InjectionPipe<Method>(list, this.service, injService)
                .filter((t, q) -> t.isAnnotationPresent(q));

        pipe.execute(target, flag);

    }

}
