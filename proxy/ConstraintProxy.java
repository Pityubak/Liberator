/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
