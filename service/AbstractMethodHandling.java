
package com.pityubak.liberator.service;

import com.pityubak.founder.Founder;
import com.pityubak.liberator.misc.Insertion;
import java.util.ArrayList;
import java.util.List;
import com.pityubak.liberator.layer.MethodConfigurationLayer;

/**
 *
 * @author Pityubak
 *
 */
public final class AbstractMethodHandling implements MethodConfigurationLayer {

    private final List<AbstractMethod> list = new ArrayList<>();

    private final Founder founder;

    public AbstractMethodHandling(Founder founder) {
        this.founder = founder;
    }

    @Override
    public MethodConfigurationLayer registrate(final Class<?> parent, final Class<?> injectedClass, final Insertion insertion) {
        final AbstractMethod method = this.list
                .stream()
                .filter(t -> t.getAbstrCls().equals(injectedClass) && t.getInsertion().equals(insertion))
                .findFirst()
                .orElse(null);
        if (method == null) {
            this.list.add(new AbstractMethod(parent, injectedClass, insertion));
        }
        return this;
    }

    boolean isPhaseExist(Insertion flag) {
        return this.list.stream().noneMatch(t -> t.getInsertion().equals(flag));
    }

    public void removeAll() {
        this.list.clear();
    }

    public void execute(Insertion flag) {
        list.stream().filter(abs -> (abs.getInsertion().equals(flag))).forEachOrdered((abs) -> {
            abs.execute(founder);
        });
    }
}
