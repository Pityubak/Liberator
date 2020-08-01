package com.pityubak.liberator.config;

/**
 *
 * @author Pityubak
 *
 */
public interface Dependency<T> {

    void createMapping(T details);

    T mapping(Class<?> cl);

    void removeMapping();
}
