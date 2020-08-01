package com.pityubak.liberator.proxy;

import java.lang.reflect.Method;

/**
 *
 * @author Pityubak
 */
public interface Machine<T> {

    void invoke(T annotation, Object instance, Method method);
}
