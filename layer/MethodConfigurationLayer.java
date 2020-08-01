package com.pityubak.liberator.layer;

import com.pityubak.liberator.misc.Insertion;

/**
 *
 * @author Pityubak
 *
 */
public interface MethodConfigurationLayer {

    MethodConfigurationLayer registrate(final Class<?> parent, 
            final Class<?> injectedClass, final Insertion insertion);
}
