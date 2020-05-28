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

import com.pityubak.liberator.exceptions.InjectionException;
import com.pityubak.liberator.misc.ModificationFlag;
import java.lang.reflect.Field;
import com.pityubak.liberator.lifecycle.InstanceService;
import com.pityubak.liberator.misc.Insertion;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;
import com.pityubak.liberator.config.MethodDependency;

/**
 *
 * @author Pityubak
 * @since 2019.02.24
 * @version 1.1
 * @see FieldInjectService
 * @see Injection
 */
public class InjectionService implements Injection {

    private final ClassInjectionService inspector;
    private final FieldInjectService fieldService;
    private final InstanceService instanceService;
    private final MethodDependency config;
    private final AbstractMethodHandling methodHandling;
    private final DetailsService service;
    private final MethodInjectService methodService;

    public InjectionService(InstanceService instanceService,
            MethodDependency config, DetailsService service,
            AbstractMethodHandling methodHandling) {
        this.instanceService = instanceService;
        this.service = service;
        this.config = config;
        this.methodHandling = methodHandling;
        this.methodService = new MethodInjectService(this.service);
        this.inspector = new ClassInjectionService(this.instanceService, this.service);
        this.fieldService = new FieldInjectService(this.instanceService, this.service);

    }

    @Override
    public void inject(final Object target, final ModificationFlag flag) {

        final List<Class<?>> annotationList = this.config.getAnnotationList(flag);
        final Class<?> injectedClass = target.getClass();
        try {

            if (injectedClass.getDeclaredAnnotations().length > 0) {
                this.inspector.inject(injectedClass, target, annotationList, flag);
            }

            for (Field f : injectedClass.getDeclaredFields()) {

                if (f.getDeclaredAnnotations().length > 0) {
                    this.fieldService.inject(f, target, annotationList, flag);
                }

            }

            for (Method m : injectedClass.getDeclaredMethods()) {

                if (m.getDeclaredAnnotations().length > 0) {
                    this.methodService.inject(m, target, annotationList, flag);
                }
            }

            this.injectAbstractMethod(flag);
        } catch (IllegalArgumentException ex) {
            throw new InjectionException("Injection failed : " + target.getClass().getName() + " : " + ex);
        }

    }

    private void injectAbstractMethod(ModificationFlag flag) {
        Insertion insertion = flag.getOneByOneInsertion();
        Consumer<Insertion> c = this.methodHandling::execute;
        c.accept(insertion);
    }

}
