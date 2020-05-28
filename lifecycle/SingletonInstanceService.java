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

import com.pityubak.liberator.config.ParameterInterceptionHandling;
import com.pityubak.liberator.data.ParameterInterception;
import com.pityubak.liberator.exceptions.ClassInstantiationException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pityubak
 * @version 0.2
 * @since 2020.01.12
 */
public final class SingletonInstanceService implements InstanceService {

    private final Map<String, Object> singletons = new HashMap<>();

    private final ParameterInterceptionHandling interception;

    private Object target;

    public SingletonInstanceService(ParameterInterceptionHandling interception) {
        this.interception = interception;
    }

    @Override
    public Object createInstance(final String name, final Class<?> cl) {

        target = singletons.get(name);

        return target == null ? getInstance(name, cl)
                : target;
    }

    private Object getInstance(final String name, final Class<?> cl) {

        //thread locking
        synchronized ((SingletonInstanceService.class)) {
            // double-checking , first check in createInstance method
            if (target == null) {
                try {
                    for (Constructor constructor : cl.getDeclaredConstructors()) {
                        final Class<?>[] paramTypes = constructor.getParameterTypes();
                        final ParameterInterception req = this.interception.getRequiredArgs(cl); 
                        if (req != null) {
                            final Object[] params = req.receive();

                            target = cl.getConstructor(paramTypes).newInstance(params);
                        } else {
                            target = cl.getConstructor().newInstance();
                        }

                    }
                    singletons.put(name, target);
                } catch (InstantiationException | IllegalAccessException
                        | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException ex) {
                    throw new ClassInstantiationException("This class: "
                            + cl.getName() + " creation failed: " + ex.getMessage());
                }

            }

        }
        return target;
    }

}
