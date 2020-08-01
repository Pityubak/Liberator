package com.pityubak.liberator.service;

import com.pityubak.liberator.data.Response;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.pityubak.liberator.proxy.ResponseProxy;

/**
 *
 * @author Pityubak
 */
public final class ClassInjectFinalizerService implements InjectFinalizerService<Class> {

    private final DetailsService methService;

    private final ResponseProxy respService;


    public ClassInjectFinalizerService(DetailsService methService, ResponseProxy respService) {
        this.methService = methService;
        this.respService = respService;
    }

    @Override
    public void finalizeMethodInvocation(final Class target, final Class<? extends Annotation> type) {

        try {
            final Method method = this.methService.processMethod();
            final Object instance = this.methService.processInstance();
            final Response args = this.respService.create();
            if (method.getReturnType().equals(Void.TYPE)) {
                method.invoke(instance, target.getAnnotation(type), args);

            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(ClassInjectFinalizerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
