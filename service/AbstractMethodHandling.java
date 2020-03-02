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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pityubak
 */
public final class AbstractMethodHandling {

    private final List<AbstractMethod> list = new ArrayList<>();

    private final InstanceService service;

    public AbstractMethodHandling(InstanceService service) {
        this.service = service;
    }

    public void registrate(final Class<?> parent, final Class<?> injectedClass, final Insertion insertion) {
        final AbstractMethod method = this.list
                .stream()
                .filter(t -> t.getAbstrCls().equals(injectedClass) && t.getInsertion().equals(insertion))
                .findFirst()
                .orElse(null);
        if (method == null) {
            this.list.add(new AbstractMethod(parent, injectedClass, insertion));
        }
    }

    public boolean isPhaseExist(Insertion flag) {
        return this.list.stream().noneMatch(t -> t.getInsertion().equals(flag));
    }

    public void removeAll() {
        this.list.clear();
    }

    public void execute(Insertion flag) {
        list.stream().filter(abs -> (abs.getInsertion().equals(flag))).forEachOrdered((abs) -> {
            abs.execute(service);
        });
    }
}
