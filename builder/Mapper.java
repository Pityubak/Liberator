package com.pityubak.liberator.builder;

import java.util.List;

/**
 *
 * @author Pityubak
 *
 */
public interface Mapper<T> {

    void createMapping(T details);

    List<T> mapping();

    void removeMapping();
}
