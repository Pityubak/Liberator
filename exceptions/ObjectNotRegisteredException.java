package com.pityubak.liberator.exceptions;

/**
 *
 * @author Pityubak
 */
public class ObjectNotRegisteredException  extends RuntimeException{

    public ObjectNotRegisteredException() {
    }

    public ObjectNotRegisteredException(String message) {
        super(message);
    }

    public ObjectNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotRegisteredException(Throwable cause) {
        super(cause);
    }

    public ObjectNotRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
}
