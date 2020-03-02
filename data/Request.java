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

import com.pityubak.liberator.exceptions.RequestInitializationException;
import com.pityubak.liberator.lifecycle.InstanceService;

/**
 *
 * @author Pityubak
 * @since 2019.12.12
 * @version 1.0
 * @param <T>-type of requested object
 */
public final class Request<T> {

    private Class<T> requestType;
    private T requestObject;
    private final InstanceService creator;

    public Request(final InstanceService creator) {
        this.creator = creator;
    }

    public void setRequestType(final Class<T> requestType) {
        this.requestType = requestType;
    }

    public T response(String name) {
        try {
            this.requestObject = (T) creator.createInstance(name, requestType);
        } catch (IllegalArgumentException ex) {

            throw new RequestInitializationException("Requested object"
                    + this.requestObject + "creation failed: " + ex.getMessage());
        }
        return this.requestObject;
    }

}
