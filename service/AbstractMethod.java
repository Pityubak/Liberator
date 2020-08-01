package com.pityubak.liberator.service;

import com.pityubak.founder.Founder;
import com.pityubak.liberator.misc.Insertion;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pityubak
 */
public final class AbstractMethod {

    private final Class<?> parentCls;
    private final Class<?> abstrCls;
    private final Insertion insertion;

    public AbstractMethod(Class<?> parentCls, Class<?> abstrCls, Insertion insertion) {
        this.parentCls = parentCls;
        this.abstrCls = abstrCls;
        this.insertion = insertion;
    }

    public void execute(final Founder founder) {
        final String clsName = parentCls.getSimpleName();
        final Object instance = founder.createSingleton(parentCls, clsName);
        for (Method m : this.abstrCls.getDeclaredMethods()) {
            try {
                m.invoke(instance, new Object[]{});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(AbstractMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Insertion getInsertion() {
        return insertion;
    }

    public Class<?> getAbstrCls() {
        return abstrCls;
    }

}
