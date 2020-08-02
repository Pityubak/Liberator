package com.pityubak.liberator.builder;

/**
 *
 * @author Pityubak
 */
public final class ConfigDetails {

    private final Class<?> targetClass;

    private final Class<?> service;

    public ConfigDetails(Class<?> targetClass, Class<?> service) {
        this.targetClass = targetClass;
        this.service = service;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Class<?> getService() {
        return service;
    }

}
