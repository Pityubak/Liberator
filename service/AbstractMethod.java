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
package com.pityubak.liberator.service;

import com.pityubak.liberator.lifecycle.InstanceService;
import com.pityubak.liberator.misc.Insertion;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pityubak
 */
public final class AbstractMethod {

    private final Class<?> parentCls;
    private final Class<?> abstrCls;
    private final Insertion insertion;

    public AbstractMethod(Class<?> parentCls, Class<?> abstrCls, Insertion insertion) {
        this.parentCls = parentCls;
        this.abstrCls = abstrCls;
        this.insertion = insertion;
    }

    public void execute(final InstanceService service) {
        final Object instance = service.createInstance(this.parentCls.getSimpleName(), this.parentCls);
        for (Method m : this.abstrCls.getDeclaredMethods()) {
            try {
                m.invoke(instance, new Object[]{});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(AbstractMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Insertion getInsertion() {
        return insertion;
    }

    public Class<?> getAbstrCls() {
        return abstrCls;
    }

}
