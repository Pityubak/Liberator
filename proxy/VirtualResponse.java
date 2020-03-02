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
package com.pityubak.liberator.proxy;

import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.data.Response;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Pityubak
 */
public final class VirtualResponse implements ResponseProxy {

    private String targetName;

    private Object value;

    private Request request;

    private String callerClassFullName;

    private String callerClassSimpleName;

    private Class<?> targetType;

    private List<Type> targetGenericTypes;

    private List<Class<?>> implementedInterfaces;

    public void setTargetName(String targetName) {
        Objects.requireNonNull(targetName, "Name is null");
        this.targetName = targetName;
    }

    public void setValue(Object value) {
        //Objects.requireNonNull(value, "Target value  is null");
        this.value = value;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setCallerClassFullName(String callerClassFullName) {
        Objects.requireNonNull(callerClassFullName, "Caller class  is null");
        this.callerClassFullName = callerClassFullName;
    }

    public void setCallerClassSimpleName(String callerClassSimpleName) {
        Objects.requireNonNull(callerClassSimpleName, "Caller class  is null");
        this.callerClassSimpleName = callerClassSimpleName;
    }

    public void setTargetType(Class<?> targetType) {
        Objects.requireNonNull(targetType, "Type of target  is null");
        this.targetType = targetType;
    }

    public void setTargetGenericTypes(List<Type> targetGenericTypes) {
        this.targetGenericTypes = targetGenericTypes;
    }

    public void setImplementedInterfaces(List<Class<?>> implementedInterfaces) {
        this.implementedInterfaces = implementedInterfaces;
    }

    @Override
    public Response create() {
        return new Response(this.targetName, this.value,
                this.request, this.callerClassFullName,
                this.callerClassSimpleName, this.targetType,
                this.targetGenericTypes, this.implementedInterfaces);
    }

}
