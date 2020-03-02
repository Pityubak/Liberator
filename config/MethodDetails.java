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
import java.util.List;

/**
 *
 * @author Pityubak
 * @since 2019.09.20
 * @version 1.0 Data wrapper
 */
public final class MethodDetails {

    private final String methodName;

    private final Class<?> className;

    private final Class<?> annotation;

    private final List<Class<?>> params;

    private final ModificationFlag modFlag;

    private final List<String> fieldNames;

    private MethodDetails(Builder builder) {
        this.methodName = builder.methodName;
        this.className = builder.className;
        this.annotation = builder.annotation;
        this.params = builder.params;
        this.modFlag = builder.modFlag;
        this.fieldNames = builder.fieldNames;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?> getClassName() {
        return className;
    }

    public Class<?> getAnnotation() {
        return annotation;
    }

    public List<Class<?>> getParams() {
        return params;
    }

    public ModificationFlag getModFlag() {
        return modFlag;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public static class Builder {

        private String methodName;

        private Class<?> className;

        private Class<?> annotation;

        private List<Class<?>> params;

        private ModificationFlag modFlag;

        private List<String> fieldNames;

        public Builder withFieldNames(List<String> fieldNames) {
            this.fieldNames = fieldNames;
            return this;
        }

        public Builder addMethodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder addClassName(Class<?> className) {
            this.className = className;
            return this;
        }

        public Builder withAnnotation(Class<?> annotation) {
            this.annotation = annotation;
            return this;
        }

        public Builder withParams(Class<?>[] params) {
            this.params = List.of(params);
            return this;
        }

        public Builder withModificationFlag(ModificationFlag modFlag) {
            this.modFlag = modFlag;
            return this;
        }

        public MethodDetails build() {
            return new MethodDetails(this);
        }

    }

}
