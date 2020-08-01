package com.pityubak.liberator.service;

import java.lang.annotation.Annotation;

/**
 *
 * @author Pityubak
 */
public interface InjectFinalizerService<C> {

    void finalizeMethodInvocation(C target, Class<? extends Annotation> type);
}
