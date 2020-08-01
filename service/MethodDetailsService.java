package com.pityubak.liberator.service;

import com.pityubak.founder.Founder;
import com.pityubak.liberator.config.MethodDetails;
import com.pityubak.liberator.misc.ModificationFlag;
import java.lang.reflect.Method;
import com.pityubak.liberator.config.MethodDependency;

/**
 *
 * @author Pityubak
 */
public final class MethodDetailsService implements DetailsService {

    private final MethodDependency config;

    private final Founder founder;

    private MethodDetails detail;

    public MethodDetailsService(MethodDependency config, Founder founder) {
        this.config = config;
        this.founder = founder;
    }

    @Override
    public Object processInstance() {
        final Class<?> cls = detail.getConcreteClass();
        final String className = cls.getSimpleName();
        return founder.createSingleton(cls, className);
    }

    @Override
    public Method processMethod() throws NoSuchMethodException {
        final Class<?> cl = detail.getConcreteClass();

        Class<?>[] params = new Class<?>[detail.getParams().size()];
        params = detail.getParams().toArray(params);

        return cl.getDeclaredMethod(detail.getMethodName(),
                params);
    }

    @Override
    public void processDetails(final Class<?> cl, final ModificationFlag flag) {
        this.detail = this.config.methodMapping(cl, flag);
    }

}
