/*
 * The MIT License
 *
 * Copyright 2019 Pityubak.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.pityubak.liberator.lifecycle;

import com.pityubak.liberator.exceptions.ClassInstantiationException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements InstanceService interface First time create instance then return
 * back always existing instance
 *
 * @author Pityubak
 * @version 1.0
 * @since 2019.09.20
 */
public final class SingletonInstanceService implements InstanceService {

    private final Map<Class<?>, Object> singletons = new HashMap<>();

   
    private  Object target;

    /**
     *
     * @param cl-target class,type:wildcard class
     * @return new Object
     * @throws IllegalArgumentException method or constructor parameters
     * mismatching
     */
    @Override
    public Object createInstance(final Class<?> cl) {

        target = singletons.get(cl);
        Constructor constructor = null;
        try {
            constructor = cl.getConstructor();
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new ClassInstantiationException("This class: "
                    + cl.getName() + " creation failed: " + ex);
        }
        return target == null ? getInstance(constructor, cl, new Object[]{})
                : target;
    }

    /**
     *
     * @param cl-target class,type:wildcard class
     * @param constructor-constructor of target class,type:Constructor
     * @param params-constructor's parameter,type: Object array
     * @return new Object
     *
     */
    private Object getInstance(final Constructor constructor, final Class<?> cl, final Object[] params) {

        //thread locking
        synchronized ((SingletonInstanceService.class)) {
            // double-checking , first check in createInstance method
            if (target == null) {
                try {
                    Class<?>[] paramTypes = constructor.getParameterTypes();
                    target = cl.getConstructor(paramTypes).newInstance(params);
                    singletons.put(cl, target);
                } catch (InstantiationException | IllegalAccessException
                        | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException ex) {
                    throw new ClassInstantiationException("This class: "
                            + cl.getName() + " creation failed: " + ex);
                }

            }

        }
        return target;
    }

}
