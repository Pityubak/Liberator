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

import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.data.Response;
import com.pityubak.liberator.lifecycle.InstanceService;
import com.pityubak.liberator.proxy.BuilderProxy;
import com.pityubak.liberator.proxy.ResponseProxy;
import com.pityubak.liberator.proxy.VirtualResponse;
import java.util.Arrays;

/**
 *
 * @author Pityubak
 */
public final class ClassResponseService implements ResponseProxy {

    private final InstanceService service;

    private final Object caller;

    private final Class targetClass;

    public ClassResponseService(InstanceService service, Object caller, Class targetClass) {
        this.service = service;
        this.caller = caller;
        this.targetClass = targetClass;
    }

    @Override
    public Response create() {

        return BuilderProxy.of(VirtualResponse::new)
                .with(VirtualResponse::setTargetName, this.targetClass.getName())
                .with(VirtualResponse::setValue, this.targetClass)
                .with(VirtualResponse::setRequest, new Request(this.service))
                .with(VirtualResponse::setCallerClassFullName, this.caller.toString())
                .with(VirtualResponse::setCallerClassSimpleName, caller.getClass().getSimpleName())
                .with(VirtualResponse::setTargetType, this.targetClass)
                .with(VirtualResponse::setImplementedInterfaces, Arrays.asList(this.targetClass.getInterfaces()))
                .build()
                .create();

    }
}
