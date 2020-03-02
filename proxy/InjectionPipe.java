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
package com.pityubak.liberator.proxy;

import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.service.DetailsService;
import com.pityubak.liberator.service.InjectFinalizerService;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 *
 * @author Pityubak
 * @param <R>
 */
public final class InjectionPipe<R> implements Pipe<R> {

    private final List<Class<?>> list;

    private final DetailsService mdService;

    private final InjectFinalizerService finalizerService;

    private BiPredicate predicate;

    public InjectionPipe(List<Class<?>> list, DetailsService mdService, InjectFinalizerService finalizerService) {
        this.list = list;
        this.mdService = mdService;
        this.finalizerService = finalizerService;
    }

    @Override
    public void execute(final R target, final ModificationFlag flag) {
        this.list.forEach(anno -> {
            Class<? extends Annotation> type = anno.asSubclass(Annotation.class);
            if (this.predicate != null) {
                if (this.predicate.test(target, type)) {
                    this.mdService.processDetails(anno, flag);
                    this.finalizerService.finalizeMethodInvocation(target, type);
                }
            }

        });
    }

    @Override
    public Pipe<R> filter(final BiPredicate<R, Class<? extends Annotation>> predicate) {
        Objects.requireNonNull(predicate, "Predicate is null");
        this.predicate = predicate;
        return this;
    }

}
