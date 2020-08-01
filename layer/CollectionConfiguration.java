
package com.pityubak.liberator.layer;

/**
 *
 * @author Pityubak
 *
 */
public interface CollectionConfiguration {

    void filter(Class<?>... args);
    
    void removeAllClasses();

}
