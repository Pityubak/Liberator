package com.pityubak.liberator.proxy;

import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.service.DetailsService;
import com.pityubak.liberator.service.InjectFinalizerService;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 *
 * @author Pityubak
 */
public final class InjectionPipe<R> implements Pipe<R> {

    private final List<Class<?>> list;

    private final DetailsService mdService;

    private final InjectFinalizerService finalizerService;

    private BiPredicate predicate;

    public InjectionPipe(List<Class<?>> list, DetailsService mdService, InjectFinalizerService finalizerService) {
        this.list = list;
        this.mdService = mdService;
        this.finalizerService = finalizerService;
    }

    @Override
    public void execute(final R target, final ModificationFlag flag) {
        this.list.forEach(anno -> {
            Class<? extends Annotation> type = anno.asSubclass(Annotation.class);
            if (this.predicate != null) {
                if (this.predicate.test(target, type)) {
                    this.mdService.processDetails(anno, flag);
                    this.finalizerService.finalizeMethodInvocation(target, type);
                }
            }

        });
    }

    @Override
    public Pipe<R> filter(final BiPredicate<R, Class<? extends Annotation>> predicate) {
        Objects.requireNonNull(predicate, "Predicate is null");
        this.predicate = predicate;
        return this;
    }

}
