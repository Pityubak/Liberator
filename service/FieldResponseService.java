package com.pityubak.liberator.service;

import com.pityubak.founder.Founder;
import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.data.Response;
import com.pityubak.liberator.proxy.BuilderProxy;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import com.pityubak.liberator.proxy.ResponseProxy;
import com.pityubak.liberator.proxy.VirtualResponse;

/**
 *
 * @author Pityubak
 */
public final class FieldResponseService implements ResponseProxy {

    private final Founder founder;

    private final Object caller;

    private final Field targetField;

    public FieldResponseService(Founder founder, Object caller, Field targetField) {
        this.founder=founder;
        this.caller = caller;
        this.targetField = targetField;
    }

    private List<Type> getCollectionType(Field f) {

        ParameterizedType pType = null;
        Type[] pTypeList = null;
        List<Type> typeList = null;
        if (Collection.class.isAssignableFrom(f.getType())) {
            pType = (ParameterizedType) f.getGenericType();
        }
        if (pType != null) {
            pTypeList = pType.getActualTypeArguments();
        }
        if (pTypeList != null) {
            typeList = Arrays.asList(pTypeList);
        }

        return typeList;
    }

    @Override
    public Response create() throws IllegalAccessException {
        AccessController.doPrivileged((PrivilegedAction) () -> {
            this.targetField.setAccessible(true);
            return null;
        });

        return BuilderProxy.of(VirtualResponse::new)
                .with(VirtualResponse::setTargetName, this.targetField.getName())
                .with(VirtualResponse::setValue, this.targetField.get(caller))
                .with(VirtualResponse::setRequest, new Request(founder))
                .with(VirtualResponse::setCallerClassFullName, this.caller.toString())
                .with(VirtualResponse::setCallerClassSimpleName, caller.getClass().getSimpleName())
                .with(VirtualResponse::setTargetType, this.targetField.getType())
                .with(VirtualResponse::setTargetGenericTypes, this.getCollectionType(targetField))
                .build() //new VirtualResponse
                .create(); // new Response

    }

}
