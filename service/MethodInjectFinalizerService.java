package com.pityubak.liberator.service;

import com.pityubak.liberator.proxy.Machine;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 *
 * @author Pityubak
 */
public final class MethodInjectFinalizerService implements InjectFinalizerService<Method> {

    private final DetailsService methService;

    private final Object caller;

    public MethodInjectFinalizerService(DetailsService methService, Object caller) {
        this.methService = methService;
        this.caller = caller;
    }

    @Override
    public void finalizeMethodInvocation(final Method target, final Class<? extends Annotation> type) {
        final Object instance = this.methService.processInstance();
        final Machine machine = (Machine) instance;
        machine.invoke(target.getAnnotation(type), this.caller, target);
    }

}
