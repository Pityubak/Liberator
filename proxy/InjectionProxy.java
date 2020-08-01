package com.pityubak.liberator.proxy;

import com.pityubak.liberator.misc.Insertion;
import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.service.AbstractMethodHandling;
import java.util.function.Consumer;

/**
 *
 * @author Pityubak
 */
public final class InjectionProxy implements InjectionStream {

    private final Consumer consumer;

    private final AbstractMethodHandling methodHandling;

    public InjectionProxy(Consumer consumer, AbstractMethodHandling methodHandling) {
        this.consumer = consumer;
        this.methodHandling = methodHandling;
    }

    @Override
    public void execute() {
        for (Insertion ins : Insertion.values()) {
            if (ins.isAggregate()) {
                this.methodHandling.execute(ins);
                if (ins.isBeforeState()) {
                    final ModificationFlag flag = ins.getFlag();
                    this.consumer.accept(flag);
                }

            }
        }

    }
}
