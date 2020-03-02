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

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Pityubak
 */
public final class Response {

    private final Long id;

    private final String targetName;

    private final Object value;

    private final Request request;

    private final String callerClassFullName;

    private final String callerClassSimpleName;

    private final Class<?> targetType;

    private final List<Type> targetGenericTypes;

    private final List<Class<?>> implementedInterfaces;

    public Response(String targetName, Object value,
            Request request, String callerClassFullName,
            String callerClassSimpleName, Class<?> targetType,
            List<Type> targetGenericTypes,
            List<Class<?>> implementedInterfaces) {
        this.targetName = targetName;
        this.value = value;
        this.request = request;
        this.callerClassFullName = callerClassFullName;
        this.callerClassSimpleName = callerClassSimpleName;
        this.targetType = targetType;
        this.targetGenericTypes = targetGenericTypes;
        this.implementedInterfaces = implementedInterfaces;
        UUID uuid = UUID.randomUUID();
        this.id = uuid.getLeastSignificantBits();
    }

    /**
     *
     * @return generated id by Liberator
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return annotated target's name Field: fieldname Class: simple name of
     * target class Method: method's name
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     *
     * @return value of annotated target field or class if response belongs to
     * method, return null, more precisely it never gets any value
     */
    public Object getValue() {
        return value;
    }

    /**
     *
     * @return Request, which create new instance any object
     */
    public Request getRequest() {
        return request;
    }

    /**
     *
     * @return full quailified name of the caller class with name of package and
     * class
     */
    public String getCallerClassFullName() {
        return callerClassFullName;
    }

    /**
     * @return type of annotated target field or class if response belongs to
     * method, return null, more precisely it never gets any value
     */
    public Class<?> getTargetType() {
        return targetType;
    }

    /**
     * @return collection, which contains generic type(s) of annotated field, if
     * it is a collection . if response belongs to method, return null, more
     * precisely it never gets any value
     */
    public List<Type> getTargetGenericTypes() {
        return targetGenericTypes;
    }

    /**
     *
     * @return simple name of the caller class
     */
    public String getCallerClassSimpleName() {
        return callerClassSimpleName;
    }

    /**
     * @return collection, witch contains implemented interfaces, when it is a
     * class. if response belongs to method, return null, more precisely it
     * never gets any value
     */
    public List<Class<?>> getImplementedInterfaces() {
        return implementedInterfaces;
    }

    @Override
    public String toString() {
        return "Response: id: " + this.getId() + ", name: " + this.getTargetName()
                + ", value: " + this.getValue()
                + ", fullname: " + this.getCallerClassFullName() + ", type: " + this.getTargetType();
    }

}
