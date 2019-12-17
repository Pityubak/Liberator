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

/**
 *
 * @author Pityubak
 * @since 2019.09.20
 * @version 1.0
 * @see DependencyConfig
 */
public class MethodDependencyConfig implements DependencyConfig {

    private final List<MethodDetails> methodDetailsList = new ArrayList<>();

    /**
     *
     * @param details ,type:MethodDteails Add new intance of MethodDetails to
     * collection
     */
    @Override
    public void createMethodMapping(MethodDetails details) {
        methodDetailsList.add(details);
    }

    /**
     *
     * @param cl,type:wildcard class
     * @return MethodDetails from list by annotation
     * @throws NoSuchElementException if collection not contains class
     *
     */
    @Override
    public MethodDetails methodMapping(Class<?> cl) {
        MethodDetails base = methodDetailsList.stream().filter(x -> x.getAnnotation().equals(cl)).findFirst().orElse(null);

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
    @Override

    public List<MethodDetails> get(ModificationFlag flag) {
        List<MethodDetails> list = new ArrayList<>();
        this.methodDetailsList.forEach(el -> {
            if (el.getModFlag().equals(flag)) {
                list.add(el);
            }
        });
        return list;
    }

}
