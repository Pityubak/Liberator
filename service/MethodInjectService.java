/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pityubak.liberator.service;

import com.pityubak.liberator.config.DependencyConfig;
import com.pityubak.liberator.config.MethodDetails;
import com.pityubak.liberator.exceptions.InjectionException;
import com.pityubak.liberator.lifecycle.InstanceService;
import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.proxy.Machine;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Pityubak Wrap source method in another
 */
public class MethodInjectService {

    private final InstanceService instanceService;
    private final DependencyConfig config;

    public MethodInjectService(InstanceService instanceService, DependencyConfig config) {
        this.instanceService = instanceService;
        this.config = config;

    }

    public void inject(Class<?> loadedClass, Method targetMethod) {
        this.config.getAnnotationList(ModificationFlag.PRIORITY_CREATION).forEach((anno) -> {
            Class<? extends Annotation> type = anno.asSubclass(Annotation.class);
            if (targetMethod.isAnnotationPresent(type)) {
                try {

                    //Make new instance of MethodDetails class through DependencyConfig
                    MethodDetails detail = this.config.methodMapping(anno);
                    //From stored data in MethodDetails
                    Class<?> cl = detail.getClassName();
                    Object instance = this.instanceService.createInstance(cl);
                    Object target = this.instanceService.createInstance(loadedClass);
                    Machine machine = (Machine) instance;
                    machine.invoke(targetMethod.getAnnotation(type), target, targetMethod, targetMethod.getParameterTypes());

                } catch (NoSuchMethodException | InstantiationException
                        | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException ex) {
                    throw new InjectionException("Injection failed : " + targetMethod.getName() + " : " + ex);
                }
            }
        });

    }

}
