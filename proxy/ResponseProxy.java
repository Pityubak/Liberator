package com.pityubak.liberator.proxy;

import com.pityubak.liberator.data.Response;

/**
 *
 * @author Pityubak
 */
public interface ResponseProxy {

    Response create() throws IllegalAccessException;
}
