package com.pityubak.liberator.service;

import com.pityubak.liberator.misc.ModificationFlag;
import java.lang.reflect.Method;

/**
 *
 * @author Pityubak
 */
public interface DetailsService {

    void processDetails(Class<?> cl, ModificationFlag flag);

    Object processInstance();

    Method processMethod() throws NoSuchMethodException;

}
