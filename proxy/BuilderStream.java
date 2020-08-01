package com.pityubak.liberator.proxy;

import java.util.function.BiConsumer;

/**
 *
 * @author Pityubak
 */
public interface BuilderStream<T> {

    <U> BuilderStream<T> with(BiConsumer<T, U> consumer, U value);

    T build();
}
