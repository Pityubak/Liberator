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


import com.pityubak.liberator.exceptions.ObjectNotRegisteredException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Pityubak
 * @since 2019.10.10
 * @version 1.0
 * @see ObserverService implementation
 *
 * Liberator use this class, that it stores field data from injected classes
 * and/or other required variables. 
 */
public class ObjectObserverService implements ObserverService {

    private final  Map<String, StoredData> fieldMap;

    public ObjectObserverService() {
        this.fieldMap = new HashMap<>();
    }

   

    
    /**
     * Registration for objects
     *
     * @param <T>
     * @param name key, type:String
     * @param type
     * @param instance
     */
    @Override
    public <T> void registration(String name, Class<T> type, T instance) {
        fieldMap.put(name, new StoredData(Objects.requireNonNull(type), instance));
    }

    /**
     * 
     * @param name
     * @param data 
     * @throws NullPointerException, when StoredData constains null value
     */
    @Override
    public  void registrationWithStoredData(String name, StoredData data) {
        Objects.requireNonNull(data.getInstance());
        Objects.requireNonNull(data.getType());
        fieldMap.put(name, data);
    }

    /**
     *
     * @param <T> value, type:expected value
     * @param name key, type:String
     * @return T ,where T is a type of expected result
     * @throws ObjectNotRegisteredException if collection not contains target object
     */
    @Override
    public <T> T getObjectByName(String name) {
        StoredData data = fieldMap.get(name);
        if (data == null) {
            throw new ObjectNotRegisteredException("This variable: " + name + " not found!");
        }
       
        return (T) data.getType().cast(data.getInstance());
    }



}
