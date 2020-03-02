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
package com.pityubak.liberator.config;

import com.pityubak.liberator.misc.ModificationFlag;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 *
 * @author Pityubak
 * @since 2019.09.20
 * @version 1.0
 * @see DependencyConfig
 */
public final class MethodDependencyConfig implements DependencyConfig {

    private final List<MethodDetails> methodDetailsList = new ArrayList<>();

    @Override
    public void createMethodMapping(final MethodDetails details) {
        methodDetailsList.add(details);
    }

    @Override
    public MethodDetails methodMapping(final Class<?> cl, final ModificationFlag flag) {

        final MethodDetails base = this.get(flag).stream()
                .filter(x -> x.getAnnotation().equals(cl))
                .findFirst()
                .orElse(null);

        if (base == null) {
            throw new NoSuchElementException("This class " + cl + " is not registered!");
        }

        return base;
    }

    /**
     *
     * @param flag, type:ModificationFlag
     * @return MethodDetails list
     */
    private List<MethodDetails> get(final ModificationFlag flag) {

        final List<MethodDetails> list = new ArrayList<>();
        this.methodDetailsList.forEach(el -> {
            if (el.getModFlag().equals(flag)) {
                list.add(el);

            }
        });
        return list;
    }

    @Override
    public List<Class<?>> getAnnotationList(final ModificationFlag flag) {

        return get(flag).stream().map(MethodDetails::getAnnotation).collect(Collectors.toList());
    }

    @Override
    public void removeMapping() {
        this.methodDetailsList.clear();
    }

}
