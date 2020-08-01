package com.pityubak.liberator.service;

import com.pityubak.liberator.misc.ModificationFlag;

/**
 *
 * @author Pityubak
 */
public interface Injection {

    void inject(Object target, ModificationFlag flag);

}
