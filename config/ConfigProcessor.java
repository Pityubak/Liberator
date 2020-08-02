package com.pityubak.liberator.config;

import com.pityubak.liberator.annotations.Config;
import com.pityubak.liberator.builder.ConfigDetails;
import com.pityubak.liberator.builder.Mapper;

/**
 *
 * @author Pityubak
 *
 */
public class ConfigProcessor implements Processor  {

    private final Mapper dependency;

    public ConfigProcessor(Mapper configDependency) {
        this.dependency = configDependency;
  
    }

    @Override
    public void registerObject(final Class<?> loadedClass) {
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
