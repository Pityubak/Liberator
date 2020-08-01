
package com.pityubak.liberator.exceptions;

/**
 * @author Pityubak
 */
public class ParameterNotExistException extends RuntimeException {

    public ParameterNotExistException() {
    }

    public ParameterNotExistException(String message) {
        super(message);
    }

    public ParameterNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterNotExistException(Throwable cause) {
        super(cause);
    }

    public ParameterNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
