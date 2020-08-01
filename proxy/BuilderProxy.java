package com.pityubak.liberator.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author Pityubak
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
