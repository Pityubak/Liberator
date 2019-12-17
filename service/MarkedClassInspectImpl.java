/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pityubak.liberator.service;

import com.pityubak.liberator.data.StoredData;
import com.pityubak.liberator.config.MethodDetails;
import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.exceptions.InjectionException;
import com.pityubak.liberator.misc.MethodFlag;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import com.pityubak.liberator.config.DependencyConfig;
import com.pityubak.liberator.data.ObserverService;
import com.pityubak.liberator.lifecycle.InstanceService;

/**
 *
 * @author Pityubak
 */
public class MarkedClassInspectImpl implements MarkedClassInspect {

    protected final InstanceService creator;
    protected final ObserverService observer;
    protected final DependencyConfig config;

    public MarkedClassInspectImpl(InstanceService creator, ObserverService observer, DependencyConfig config) {
        this.creator = creator;
        this.observer = observer;
        this.config = config;
    }

    @Override
    public void getMarkedClass(Class<?> cl, List<Class<?>> list) {

        for (Class<?> anno : list) {
            //Make new instance 
            Class<? extends Annotation> type = anno.asSubclass(Annotation.class);
            if (cl.isAnnotationPresent(type)) {
                try {
                    //Make new instance of MethodDetails class through DependencyConfig
                    MethodDetails detail = this.config.methodMapping(anno);

                    //From stored data in MethodDetails
                    Class<?> cls = detail.getClassName();
                    Object instance = this.creator.createInstance(cls);
                    Class<?>[] params = new Class<?>[detail.getParams().size()];
                    params = detail.getParams().toArray(params);
                    Method method = cls.getDeclaredMethod(detail.getMethodName(),
                            params);
                   
                    MethodFlag flag = detail.getMethodFlag();

                    Flag flagBase = new Flag.Builder(cl.getAnnotation(type))
                            .withRequest(new Request(this.creator))
                            .withObserver(observer)
                            .withName(cl.getSimpleName())
                            .withValue(cl)
                            .build();

                    Process processor = new FlagProcess(flagBase);

                    Object[] args = processor.process(flag);
                    if (method.getReturnType().equals(Void.TYPE)) {
                        method.invoke(instance, args);

                    }

                    Object typeInstance = this.creator.createInstance(cl);
                    StoredData data = new StoredData(typeInstance.getClass(), typeInstance);

                    //Field name and value stored in ObserverService
                    observer.registrationWithStoredData(cl.getSimpleName(), data);
                } catch (NoSuchMethodException | InstantiationException
                        | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException ex) {
                    throw new InjectionException("Injection failed : " + cl.getName() + " : " + ex);
                }
            }
        }

    }

}
