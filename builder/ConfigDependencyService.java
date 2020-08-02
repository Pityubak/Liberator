package com.pityubak.liberator.builder;

import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Pityubak
 */
public class ConfigDependencyService {

    private final Mapper<ConfigDetails> mapper;

    public ConfigDependencyService(Mapper<ConfigDetails> mapper) {
        this.mapper = mapper;
    }

    public ConfigDetails mapping(Class<?> cl) {
        List<ConfigDetails> details = mapper.mapping();
        final ConfigDetails base = details.stream()
                .filter(x -> x.getTargetClass().equals(cl))
                .findFirst()
                .orElse(null);

        if (base == null) {
            throw new NoSuchElementException("This class " + cl + " is not registered!");
        }

        return base;

    }
}
