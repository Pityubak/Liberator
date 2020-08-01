package com.pityubak.liberator.proxy;

import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.service.InjectionService;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Pityubak
 */
public final class InjectConsumer implements Consumer<ModificationFlag> {

    private final List<Object> injectedObjectList;
    private final InjectionService service;

    public InjectConsumer(List<Object> injectedObjectList, InjectionService service) {
        this.injectedObjectList = injectedObjectList;
        this.service = service;
    }

    @Override
    public void accept(final ModificationFlag t) {
        this.injectedObjectList.forEach(m -> this.service.inject(m, t));
    }

}
