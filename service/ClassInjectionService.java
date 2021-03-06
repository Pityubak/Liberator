package com.pityubak.liberator.service;

import com.pityubak.founder.Founder;
import java.util.List;


import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.proxy.InjectionPipe;
import com.pityubak.liberator.proxy.Pipe;
import com.pityubak.liberator.proxy.ResponseProxy;

/**
 *
 * @author Pityubak
 */
public final class ClassInjectionService implements ExecutorService<Class<?>> {

    private final Founder founder;
    private final DetailsService service;

    public ClassInjectionService(Founder founder, DetailsService service) {
        this.founder=founder;
        this.service = service;
    }

    @Override
    public void inject(final Class<?> cl, final Object typeInstance, final List<Class<?>> list, final ModificationFlag flag) {

        final ResponseProxy responseService = new ClassResponseService(founder, typeInstance, cl);
        final InjectFinalizerService injService = new ClassInjectFinalizerService(this.service, responseService);

        final Pipe<Class> pipe = new InjectionPipe<Class>(list, this.service, injService)
                .filter((t, q) -> t.isAnnotationPresent(q));

        pipe.execute(cl, flag);

    }
}
