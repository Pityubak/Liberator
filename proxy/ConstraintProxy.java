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

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 *
 * @author Pityubak
 * @param <T>
 *
 */
public final class ConstraintProxy<T> implements ConstraintStream<T> {

    private T firstValue;

    private Predicate predicate;

    private ConstraintProxy(final T value) {
        this.firstValue = value;

    }

    public static <T> ConstraintStream<T> stream(final T t) {
        return new ConstraintProxy(t);
    }

    @Override
    public ConstraintStream<T> filter(final Predicate predicate) {
        this.predicate = predicate;
        return this;
    }

    @Override
    public T get() {
        return this.firstValue;
    }

    @Override
    public ConstraintStream<T> reduce(final UnaryOperator function) {
        if (this.predicate != null) {
            if (this.predicate.test(this.firstValue)) {
                this.firstValue = (T) function.apply(this.firstValue);
            }
        } else {
            this.firstValue = (T) function.apply(this.firstValue);
        }
        return this;
    }

    @Override
    public <Q> ConstraintStream<T> reduce(final Consumer<? super Q> function, final Q value) {
        if (this.predicate != null) {
            if (this.predicate.test(this.firstValue)) {
                function.accept(value);
            }
        } else {
            function.accept(value);
        }
        return this;
    }

    @Override
    public <R> ConstraintStream<T> reduce(final Consumer<? super T> function, Predicate<? super R> predicate, final R test) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(function);
        if (predicate.test(test)) {
            function.accept(this.firstValue);
        }

        return this;
    }

}
