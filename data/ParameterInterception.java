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
package com.pityubak.liberator.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Pityubak
 */
public final class ParameterInterception implements Interception {

    private final List<Data> args = new ArrayList<>();

    private final Class<?> cl;

    public ParameterInterception(Class<?> cl) {
        this.cl = cl;
    }

    @Override
    public <T> void registrate(final T value, final boolean condition) {
        this.args.add(new Data(value.getClass(), value, condition));
    }

    @Override
    public <T> void registrate(final T value) {
        this.args.add(new Data(value.getClass(), value));
    }

    @Override
    public Object[] receive() {
        final Object[] params = new Object[this.args.size()];
        for (int i = 0; i < params.length; i++) {
            Data data = args.get(i);
            params[i] = data.getInstance();
        }
        return params;
    }

    @Override
    public Class<?> getTarget() {
        return this.cl;
    }

    @Override
    public void clearRemoveableData() {
        Iterator it = this.args.iterator();
        while (it.hasNext()) {
            Data data = (Data) it.next();
            if (data.isCondition()) {
                it.remove();
            }
        }
    }

}
