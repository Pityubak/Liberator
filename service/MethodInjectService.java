package com.pityubak.liberator.service;

import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.proxy.InjectionPipe;
import com.pityubak.liberator.proxy.Pipe;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Pityubak 
 */
public final class MethodInjectService implements ExecutorService<Method> {

    private final DetailsService service;

    public MethodInjectService(DetailsService service) {
        this.service = service;

    }

    @Override
    public void inject(final Method target, final Object obj, final List<Class<?>> list, final ModificationFlag flag) {

        final InjectFinalizerService injService = new MethodInjectFinalizerService(service, obj);
        final Pipe<Method> pipe = new InjectionPipe<Method>(list, this.service, injService)
                .filter((t, q) -> t.isAnnotationPresent(q));

        pipe.execute(target, flag);

    }

}
