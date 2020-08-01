package com.pityubak.liberator.config;

/**
 *
 * @author Pityubak
 *
 */
public class MethodResolverService implements Resolver {

    private final MethodDependency deps;

    public MethodResolverService(MethodDependency deps) {
        this.deps = deps;
    }
    
    
    @Override
    public void resolve(Class<?> cl) {
       this.deps.removeMapping();
    }
    
}
