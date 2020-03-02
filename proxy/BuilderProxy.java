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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author Pityubak
 * @param <T>
 */
public class BuilderProxy<T> implements BuilderStream<T> {

    private final Supplier<T> initSupplier;

    private final List<Consumer<T>> variablesModifiers = new ArrayList<>();

    private BuilderProxy(Supplier<T> initSupplier) {
        this.initSupplier = initSupplier;
    }

    public static <T> BuilderProxy<T> of(final Supplier<T> instantiator) {
        return new BuilderProxy<>(instantiator);
    }

    @Override
    public <U> BuilderProxy<T> with(final BiConsumer<T, U> consumer, final U value) {
        final Consumer<T> c = instance -> consumer.accept(instance, value);
        variablesModifiers.add(c);
        return this;
    }

    @Override
    public T build() {
        final T value = initSupplier.get();
        variablesModifiers.forEach(modifier -> modifier.accept(value));
        variablesModifiers.clear();
        return value;
    }
}
