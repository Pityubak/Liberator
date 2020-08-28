package com.pityubak.liberator.service;

import com.pityubak.founder.Founder;
import java.lang.reflect.Field;
import java.util.List;
import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.proxy.InjectionPipe;
import com.pityubak.liberator.proxy.Pipe;
import com.pityubak.liberator.proxy.ResponseProxy;

/**
 *
 * @author Pityubak
 */
public final class FieldInjectService implements ExecutorService<Field> {

    private final Founder founder;
    private final DetailsService service;

    public FieldInjectService(Founder founder, DetailsService service) {
        this.founder=founder;
        this.service = service;

    }

    @Override
    public void inject(final Field f, final Object obj, final List<Class<?>> list, final ModificationFlag flag) {
        final ResponseProxy responseService = new FieldResponseService(founder, obj, f);
        final FieldInjectFinalizerService injService = new FieldInjectFinalizerService(service, responseService, obj);
        final Pipe<Field> pipe = new InjectionPipe<Field>(list, this.service, injService)
                .filter((t, q) -> t.isAnnotationPresent(q));

        pipe.execute(f, flag);

    }

}
