package com.pityubak.liberator.builder;

import com.pityubak.liberator.annotations.Config;
import com.pityubak.liberator.config.ConfigDetails;
import com.pityubak.liberator.config.ConfigurationService;
import com.pityubak.liberator.config.Dependency;

/**
 *
 * @author Pityubak
 *
 */
public class ConfigProcessor  {

    private final Dependency dependency;

    public ConfigProcessor(Dependency configDependency) {
        this.dependency = configDependency;
  
    }

    public void registerConfigObject(final Class<?> loadedClass) {
        if (loadedClass.isAnnotationPresent(Config.class)) {
            this.createConfigDetails(loadedClass);
        } 
    }

    private void createConfigDetails(final Class<?> loadedClass) {
        final Config config = loadedClass.getAnnotation(Config.class);
        if (!loadedClass.getInterfaces()[0].equals(ConfigurationService.class)) {
            throw new IllegalArgumentException("Config class:" + loadedClass.getSimpleName()
                    + "must implement ConfigurationService interface ");
        }
        ConfigDetails details = new ConfigDetails(config.value(), loadedClass);
        this.dependency.createMapping(details);
    }



}
