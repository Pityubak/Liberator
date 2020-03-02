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

import com.pityubak.liberator.data.Response;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.pityubak.liberator.proxy.ResponseProxy;

/**
 *
 * @author Pityubak
 */
public final class FieldInjectFinalizerService implements InjectFinalizerService<Field> {

    private final DetailsService methService;

    private final ResponseProxy respService;

    private final Object caller;

    private final List<Class<?>> TYPE = Arrays.asList(new Class<?>[]{Integer.class, Boolean.class,
        Short.class, Byte.class, Long.class, Double.class, Float.class, Character.class});

    public FieldInjectFinalizerService(DetailsService methService, ResponseProxy respService, Object caller) {
        this.methService = methService;
        this.respService = respService;
        this.caller = caller;
    }

    @Override
    public void finalizeMethodInvocation(final Field f, final Class<? extends Annotation> type) {

        try {
            final Method method = this.methService.processMethod();
            final Object instance = this.methService.processInstance();
            final Response args = this.respService.create();

            if (method.getReturnType().equals(Void.TYPE)) {

                method.invoke(instance, f.getAnnotation(type), args);

            } else {
                final Object param = method.invoke(instance, f.getAnnotation(type), args);
                if (method.getReturnType().equals(f.getType())
                        || f.getType().isAssignableFrom(param.getClass())
                        || this.TYPE.contains(param.getClass())) {
                    f.set(this.caller, param);

                }

            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(FieldInjectFinalizerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
