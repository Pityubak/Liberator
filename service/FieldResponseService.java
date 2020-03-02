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
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import com.pityubak.liberator.proxy.ResponseProxy;
import com.pityubak.liberator.proxy.VirtualResponse;

/**
 *
 * @author Pityubak
 */
public final class FieldResponseService implements ResponseProxy {

    private final InstanceService service;

    private final Object caller;

    private final Field targetField;

    public FieldResponseService(InstanceService service, Object caller, Field targetField) {
        this.service = service;
        this.caller = caller;
        this.targetField = targetField;
    }

    private List<Type> getCollectionType(Field f) {

        ParameterizedType pType = null;
        Type[] pTypeList = null;
        List<Type> typeList = null;
        if (Collection.class.isAssignableFrom(f.getType())) {
            pType = (ParameterizedType) f.getGenericType();
        }
        if (pType != null) {
            pTypeList = pType.getActualTypeArguments();
        }
        if (pTypeList != null) {
            typeList = Arrays.asList(pTypeList);
        }

        return typeList;
    }

    @Override
    public Response create() throws IllegalAccessException {
        AccessController.doPrivileged((PrivilegedAction) () -> {
            this.targetField.setAccessible(true);
            return null;
        });

        return BuilderProxy.of(VirtualResponse::new)
                .with(VirtualResponse::setTargetName, this.targetField.getName())
                .with(VirtualResponse::setValue, this.targetField.get(caller))
                .with(VirtualResponse::setRequest, new Request(this.service))
                .with(VirtualResponse::setCallerClassFullName, this.caller.toString())
                .with(VirtualResponse::setCallerClassSimpleName, caller.getClass().getSimpleName())
                .with(VirtualResponse::setTargetType, this.targetField.getType())
                .with(VirtualResponse::setTargetGenericTypes, this.getCollectionType(targetField))
                .build() //new VirtualResponse
                .create(); // new Response

    }

}
