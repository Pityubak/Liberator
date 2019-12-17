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

import com.pityubak.liberator.misc.MethodFlag;
import com.pityubak.liberator.proxy.ConstraintProxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Pityubak
 * @version 1.0
 * Create injected method's parameters by flag
 */
public final class FlagProcess implements Process {

    private final Flag flag;

    public FlagProcess(Flag flag) {
        this.flag = flag;
    }

    /**
     * 
     * @param flag-MethodFlag type
     * @return array of Object, this is arguments 
     * of injected method
     * Use ConstraintProxy
     */
    @Override
    public Object[] process(MethodFlag flag) {
        String[] flags = flag.name().split("_");
        List<String> flagList = Arrays.asList(flags);
        List<Object> params = new ArrayList<>();

        params.add(this.flag.getAnnonation());
        ConstraintProxy.stream(flagList)
                .filter(t -> t.contains("REQUEST"))
                .reduce(params::add, this.flag.getRequest())
                .filter(t -> t.contains("OBSERVER"))
                .reduce(params::add, this.flag.getObserver())
                .filter(t -> t.contains("NAME"))
                .reduce(params::add, this.flag.getName())
                .filter(t -> t.contains("VALUE"))
                .reduce(params::add, this.flag.getValue());

        return params.toArray();
    }

}
