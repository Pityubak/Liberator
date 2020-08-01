package com.pityubak.liberator.config;

import com.pityubak.founder.Founder;
import java.lang.annotation.Target;
import com.pityubak.liberator.layer.CollectionConfiguration;
import com.pityubak.liberator.service.AbstractMethodHandling;

/**
 *
 * @author Pityubak
 */
public class ConfigResolverService implements Resolver {

    private final Dependency config;
    private final CollectionConfiguration collection;
    private final Founder founder;
    private final AbstractMethodHandling methodHandling;

    public ConfigResolverService(Dependency config, CollectionConfiguration collection,
            Founder founder, AbstractMethodHandling methodHandling) {
        this.config = config;
        this.collection = collection;
        this.founder = founder;
        this.methodHandling = methodHandling;
    }

    @Override
    public void resolve(final Class<?> cl) {
        collection.removeAllClasses();
        this.methodHandling.removeAll();
        final ConfigDetails details = (ConfigDetails) this.config.mapping(cl);
        final Class<?> service = details.getService();
        final String serviceName = service.getSimpleName();
        final ConfigurationService configService
                = (ConfigurationService) founder.createSingleton(service, serviceName);

        configService.filter(collection);
        configService.registerAbstractMethod(methodHandling);
    }

}
