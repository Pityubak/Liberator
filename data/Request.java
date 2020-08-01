package com.pityubak.liberator.data;

import com.pityubak.founder.Founder;
import com.pityubak.liberator.exceptions.RequestInitializationException;

/**
 *
 * @author Pityubak
 */
public final class Request<T> {

    private Class<T> requestType;
    private T requestObject;
    private final Founder founder;

    public Request(final Founder founder) {
        this.founder = founder;
    }

    public void setRequestType(final Class<T> requestType) {
        this.requestType = requestType;
    }

    public T response(String name) {
        try {
            this.requestObject = (T) founder.createSingleton(requestType, name);
        } catch (IllegalArgumentException ex) {

            throw new RequestInitializationException("Requested object"
                    + this.requestObject + "creation failed: " + ex.getMessage());
        }
        return this.requestObject;
    }

}
