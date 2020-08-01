package com.pityubak.liberator;

import com.pityubak.founder.Founder;
import com.pityubak.liberator.proxy.InjectConsumer;
import com.pityubak.liberator.service.InjectionService;
import com.pityubak.liberator.proxy.InjectionProxy;
import com.pityubak.liberator.proxy.InjectionStream;
import com.pityubak.liberator.service.AbstractMethodHandling;
import com.pityubak.liberator.service.DetailsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.pityubak.liberator.config.MethodDependency;

/**
 * @author Pityubak
 */
public class Container {

    private final Founder founder;
    private final MethodDependency methodDependency;
    private final InjectionService injectionService;
    private final AbstractMethodHandling methodHandler;
    private final DetailsService detailsService;

    public Container(Founder founder, MethodDependency methodDependency,
            DetailsService detailsService, AbstractMethodHandling methodHandling) {
        this.founder = founder;
        this.methodDependency = methodDependency;
        this.detailsService = detailsService;
        this.methodHandler = methodHandling;
        this.injectionService = new InjectionService(this.founder, this.methodDependency, this.detailsService, this.methodHandler);
    }

    public void inject(final Map<String, Class<?>> injectedClasses) {

        final List<Object> namedObjectList = new ArrayList<>();
        injectedClasses.keySet().forEach(name -> {
            Class<?> cls = injectedClasses.get(name);
            namedObjectList.add(founder.createSingleton(cls, name));
        });
        //
        this.inject(namedObjectList);
    }

    public void inject(final List<Object> injectedClass) {

        final Consumer consumer = new InjectConsumer(injectedClass, this.injectionService);
        final InjectionStream injectionStream = new InjectionProxy(consumer, this.methodHandler);
        injectionStream.execute();

    }

}
