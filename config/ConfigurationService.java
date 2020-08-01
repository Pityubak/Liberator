package com.pityubak.liberator.config;

import com.pityubak.liberator.layer.MethodConfigurationLayer;
import com.pityubak.liberator.layer.CollectionConfiguration;

/**
 *
 * @author Pityubak
 *
 */
public interface ConfigurationService {

    default void filter(CollectionConfiguration collection) {
        System.out.println("Default Configuration");
    }

    default void registerAbstractMethod(MethodConfigurationLayer methods) {
        System.out.println("Default Method registration");
    }
;
}
