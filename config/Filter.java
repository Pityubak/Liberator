package com.pityubak.liberator.config;

import java.util.List;

/**
 *
 * @author Pityubak
 */
public interface Filter {

    public void filter(Class<?>... classes);

    public List<Class<?>> getFinalClassList();
    
    public void removeAllClasses();
}
