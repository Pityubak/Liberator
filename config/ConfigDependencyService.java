/*
 * The MIT License
 *
 * Copyright 2020 Pityubak.
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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Pityubak
 * @since 2020.05.18
 * @version 1.0
 *
 */
public class ConfigDependencyService implements Dependency<ConfigDetails> {

    private final List<ConfigDetails> list = new ArrayList<>();

    @Override
    public void createMapping(ConfigDetails details) {
        this.list.add(details);
    }

    @Override
    public ConfigDetails mapping(Class<?> cl) {
        final ConfigDetails base = this.list.stream()
                .filter(x -> x.getTargetClass().equals(cl))
                .findFirst()
                .orElse(null);

        if (base == null) {
            throw new NoSuchElementException("This class " + cl + " is not registered!");
        }

        return base;
    }

    @Override
    public void removeMapping() {
        this.list.clear();
    }

}
