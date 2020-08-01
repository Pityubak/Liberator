package com.pityubak.liberator;

import com.pityubak.collier.Collier;
import com.pityubak.collier.misc.ParsingTarget;
import com.pityubak.founder.Founder;
import com.pityubak.founder.data.Parameter;
import com.pityubak.liberator.builder.ConfigProcessor;
import com.pityubak.liberator.builder.MethodProcessor;
import com.pityubak.liberator.config.ClassFilter;

import com.pityubak.liberator.config.MethodDependencyService;

import java.util.List;
import com.pityubak.liberator.config.ConfigResolverService;
import com.pityubak.liberator.config.Dependency;
import com.pityubak.liberator.config.ConfigDependencyService;
import com.pityubak.liberator.config.Filter;
import com.pityubak.liberator.config.MainResolverService;
import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.service.AbstractMethodHandling;
import com.pityubak.liberator.service.DetailsService;
import com.pityubak.liberator.service.MethodDetailsService;

import java.util.HashMap;
import java.util.Map;
import com.pityubak.liberator.config.MethodDependency;
import com.pityubak.liberator.config.MethodResolverService;
import com.pityubak.liberator.config.Resolver;
import java.util.Collections;
import com.pityubak.liberator.layer.CollectionConfiguration;
import com.pityubak.liberator.layer.CollectionConfigurationLayer;

/**
 *
 * @author Pityubak
 */
public final class Liberator {

    private final Founder founder;
    private final Collier collier;
    private final Container container;
    private final MethodDependency methodDependency;

    private final Dependency configDependency;
    private final Resolver configResolver;
    private final Resolver methodResolver;
    private final Resolver mainResolver;
    private final DetailsService detailsService;
    private final AbstractMethodHandling methodHandler;
    private final ConfigProcessor configProcessor;
    private final MethodProcessor methodProcessor;

    private final CollectionConfiguration colleectionConfiguration;
    private final Filter filter;

    public Liberator(final Class<?> cl) {
        this.founder = new Founder();
        this.collier = new Collier(cl, ParsingTarget.CLASS_FILE);
        this.filter = new ClassFilter(collier);
        this.colleectionConfiguration = new CollectionConfigurationLayer(filter);
        this.configDependency = new ConfigDependencyService();

        this.configProcessor = new ConfigProcessor(this.configDependency);
        this.methodDependency = new MethodDependencyService();
        this.methodProcessor = new MethodProcessor(methodDependency);
        this.methodHandler = new AbstractMethodHandling(founder);
        this.configResolver = new ConfigResolverService(this.configDependency,
                this.colleectionConfiguration, founder, this.methodHandler);
        this.methodResolver = new MethodResolverService(this.methodDependency);
        this.mainResolver = new MainResolverService(this.configResolver, this.methodResolver);
        this.detailsService = new MethodDetailsService(this.methodDependency, founder);
        this.container = new Container(founder, this.methodDependency, this.detailsService, this.methodHandler);
        this.filter.getFinalClassList().forEach(t -> {
            configProcessor.registerConfigObject((Class<?>) t);
        });

    }

    public void init(final Class<?> cl) {
        this.mainResolver.resolve(cl);
        filter.getFinalClassList().forEach(t -> {
            methodProcessor.registerMethodObject((Class<?>) t);
        });

    }

    public void registerParameterForInit(final Class<?> cls, final Parameter prm) {
        founder.registerParameter(cls, prm);

    }

    public List<Class<?>> getClassListFromTargetPackage(final Class<?> mainClass) {
        return Collections.unmodifiableList(filter.getFinalClassList());

    }

    public Request askForRequest() {
        return new Request(founder);
    }

    public void inject(final List<Object> injectedList) {
        this.container.inject(injectedList);
    }

    public void inject(final Map<String, Class<?>> map) {
        this.container.inject(map);
    }

    public void inject(final Class<?>... classes) {
        final Map<String, Class<?>> map = new HashMap<>();
        for (Class<?> cl : classes) {
            map.put(cl.getSimpleName(), cl);

        }
        this.container.inject(map);
    }

}
