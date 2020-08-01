package com.pityubak.liberator.layer;

import com.pityubak.liberator.config.Filter;

/**
 *
 * @author Pityubak
 */
public class CollectionConfigurationLayer implements CollectionConfiguration {

    private final Filter filter;

    public CollectionConfigurationLayer(Filter filter) {
        this.filter = filter;
    }

    @Override
    public void filter(Class<?>... args) {
        filter.filter(args);
    }

    @Override
    public void removeAllClasses() {
        filter.removeAllClasses();
    }

}
