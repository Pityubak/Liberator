package com.pityubak.liberator.config;

/**
 *
 * @author Pityubak
 *
 */
public class MainResolverService implements Resolver {

    private final Resolver configService;
    
    private final Resolver methodService;

    public MainResolverService(Resolver configService, Resolver methodService) {
        this.configService = configService;
        this.methodService = methodService;
    }
    
    
    @Override
    public void resolve(Class<?> cl) {
        this.configService.resolve(cl);
        this.methodService.resolve(cl);
    }
    
}
