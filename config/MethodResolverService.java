package com.pityubak.liberator.config;

import com.pityubak.liberator.builder.Mapper;

/**
 *
 * @author Pityubak
 *
 */
public class MethodResolverService implements Resolver {

    private final Mapper mapper;

    public MethodResolverService(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void resolve(Class<?> cl) {
        mapper.removeMapping();
    }

}
