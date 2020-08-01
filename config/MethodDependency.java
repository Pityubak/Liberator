package com.pityubak.liberator.config;

import com.pityubak.liberator.misc.ModificationFlag;
import java.util.List;

/**
 *
 * @author Pityubak
 */
public interface MethodDependency {

    MethodDetails methodMapping(Class<?> cl, ModificationFlag flag);

    List<Class<?>> getAnnotationList(ModificationFlag flag);

    void createMethodMapping(MethodDetails details);

    void removeMapping();
}
