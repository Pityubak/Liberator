package com.pityubak.liberator.proxy;

import com.pityubak.liberator.misc.ModificationFlag;
import java.lang.annotation.Annotation;
import java.util.function.BiPredicate;

/**
 *
 * @author Pityubak
 */
public interface Pipe<R> {

    void execute(R let, ModificationFlag flag);

    Pipe<R> filter(BiPredicate<R, Class<? extends Annotation>> predicate);

}
