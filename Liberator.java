package com.pityubak.liberator;

import com.pityubak.collier.Collier;
import com.pityubak.collier.misc.ParsingTarget;
import com.pityubak.founder.Founder;
import com.pityubak.founder.data.Parameter;
import com.pityubak.liberator.config.ConfigProcessor;
import com.pityubak.liberator.config.MethodProcessor;
import com.pityubak.liberator.config.ClassFilter;

import com.pityubak.liberator.builder.MethodDependencyService;

import java.util.List;
import com.pityubak.liberator.config.ConfigResolverService;
import com.pityubak.liberator.builder.ConfigDependencyService;
import com.pityubak.liberator.builder.ConfigDetails;
import com.pityubak.liberator.config.Filter;
import com.pityubak.liberator.config.MainResolverService;
import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.service.AbstractMethodHandling;
import com.pityubak.liberator.service.DetailsService;
import com.pityubak.liberator.service.MethodDetailsService;

import java.util.HashMap;
import java.util.Map;
import com.pityubak.liberator.config.MethodResolverService;
import com.pityubak.liberator.config.Resolver;
import java.util.Collections;
import com.pityubak.liberator.layer.CollectionConfiguration;
import com.pityubak.liberator.layer.CollectionConfigurationLayer;
import com.pityubak.liberator.builder.Mapper;
import com.pityubak.liberator.builder.MethodDetails;
import com.pityubak.liberator.builder.ObjectMapper;

/**
 *
 * @author Pityubak
 */
public final class Liberator {

    private final Founder founder;
    private final Container container;
    private final Resolver mainResolver;
    private final MethodProcessor methodProcessor;
    private final Filter filter;

    public Liberator(final Class<?> cl) {
        Collier collier = new Collier(cl, ParsingTarget.CLASS_FILE);
        Mapper<ConfigDetails> configMapper = new ObjectMapper<>();
        Mapper<MethodDetails> methodMapper = new ObjectMapper<>();
        ConfigDependencyService configDependency = new ConfigDependencyService(configMapper);
        ConfigProcessor configProcessor = new ConfigProcessor(configMapper);
        MethodDependencyService methodDependency = new MethodDependencyService(methodMapper);
        Resolver methodResolver = new MethodResolverService(methodMapper);

        this.founder = new Founder();
        this.filter = new ClassFilter(collier);
        CollectionConfiguration collectionConfiguration = new CollectionConfigurationLayer(filter);
        this.methodProcessor = new MethodProcessor(methodMapper);
        AbstractMethodHandling methodHandler = new AbstractMethodHandling(founder);
        Resolver configResolver = new ConfigResolverService(configDependency,
                collectionConfiguration, founder, methodHandler);

        this.mainResolver = new MainResolverService(configResolver, methodResolver);
        DetailsService detailsService = new MethodDetailsService(methodDependency, founder);
        this.container = new Container(founder, methodDependency, detailsService, methodHandler);
        this.filter.getFinalClassList().forEach(t -> {
            configProcessor.registerObject((Class<?>) t);
        });

    }

    public void init(final Class<?> cl) {
        this.mainResolver.resolve(cl);
        filter.getFinalClassList().forEach(t -> {
            methodProcessor.registerObject((Class<?>) t);
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
