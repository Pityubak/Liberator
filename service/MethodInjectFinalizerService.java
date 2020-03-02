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

import com.pityubak.liberator.proxy.Machine;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 *
 * @author Pityubak
 */
public final class MethodInjectFinalizerService implements InjectFinalizerService<Method> {

    private final DetailsService methService;

    private final Object caller;

    public MethodInjectFinalizerService(DetailsService methService, Object caller) {
        this.methService = methService;
        this.caller = caller;
    }

    @Override
    public void finalizeMethodInvocation(final Method target, final Class<? extends Annotation> type) {
        final Object instance = this.methService.processInstance();
        final Machine machine = (Machine) instance;
        machine.invoke(target.getAnnotation(type), this.caller, target);
    }

}
