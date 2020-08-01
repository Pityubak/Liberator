package com.pityubak.liberator.config;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Pityubak
 */
public class ConfigDependencyService implements Dependency<ConfigDetails> {

    private final List<ConfigDetails> list = new ArrayList<>();

    @Override
    public void createMapping(ConfigDetails details) {
        this.list.add(details);
    }

    @Override
    public ConfigDetails mapping(Class<?> cl) {
        final ConfigDetails base = this.list.stream()
                .filter(x -> x.getTargetClass().equals(cl))
                .findFirst()
                .orElse(null);

        if (base == null) {
            throw new NoSuchElementException("This class " + cl + " is not registered!");
        }

        return base;
    }

    @Override
    public void removeMapping() {
        this.list.clear();
    }

}
