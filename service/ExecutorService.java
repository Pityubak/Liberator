package com.pityubak.liberator.service;

import com.pityubak.liberator.misc.ModificationFlag;
import java.util.List;

/**
 *
 * @author Pityubak
 */
public interface ExecutorService<R> {

    void inject(R target, Object obj, List<Class<?>> list, ModificationFlag flag);
}
