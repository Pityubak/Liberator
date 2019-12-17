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
import java.lang.annotation.Annotation;
import com.pityubak.liberator.data.ObserverService;

/**
 *
 * @author Pityubak
 */
public final class Flag {

    private final Annotation annonation;

    private final String name;

    private final Object value;

    private final Request request;

    private final ObserverService observer;

    private Flag(Builder builder) {
        this.annonation = builder.annotation;
        this.name = builder.name;
        this.value = builder.value;
        this.request = builder.request;
        this.observer = builder.observer;

    }

    public Annotation getAnnonation() {
        return annonation;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public Request getRequest() {
        return request;
    }

    public ObserverService getObserver() {
        return observer;
    }

    public static class Builder {

        private final Annotation annotation;

        private String name;

        private Object value;

        private Request request;

        private ObserverService observer;

        public Builder(Annotation annotation) {
            this.annotation = annotation;
        }

        public Builder withName(String name) {

            this.name = name;

            return this;
        }

        public Builder withValue(Object value) {

            this.value = value;

            return this;
        }

        public Builder withRequest(Request request) {

            this.request = request;

            return this;
        }

        public Builder withObserver(ObserverService observer) {

            this.observer = observer;

            return this;
        }

        public Flag build() {
            return new Flag(this);
        }
    }
}
