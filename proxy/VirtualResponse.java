package com.pityubak.liberator.proxy;

import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.data.Response;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Pityubak
 */
public final class VirtualResponse implements ResponseProxy {

    private String targetName;

    private Object value;

    private Request request;

    private String callerClassFullName;

    private String callerClassSimpleName;

    private Class<?> targetType;

    private List<Type> targetGenericTypes;

    private List<Class<?>> implementedInterfaces;

    public void setTargetName(String targetName) {
        Objects.requireNonNull(targetName, "Name is null");
        this.targetName = targetName;
    }

    public void setValue(Object value) {
        //Objects.requireNonNull(value, "Target value  is null");
        this.value = value;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setCallerClassFullName(String callerClassFullName) {
        Objects.requireNonNull(callerClassFullName, "Caller class  is null");
        this.callerClassFullName = callerClassFullName;
    }

    public void setCallerClassSimpleName(String callerClassSimpleName) {
        Objects.requireNonNull(callerClassSimpleName, "Caller class  is null");
        this.callerClassSimpleName = callerClassSimpleName;
    }

    public void setTargetType(Class<?> targetType) {
        Objects.requireNonNull(targetType, "Type of target  is null");
        this.targetType = targetType;
    }

    public void setTargetGenericTypes(List<Type> targetGenericTypes) {
        this.targetGenericTypes = targetGenericTypes;
    }

    public void setImplementedInterfaces(List<Class<?>> implementedInterfaces) {
        this.implementedInterfaces = implementedInterfaces;
    }

    @Override
    public Response create() {
        return new Response(this.targetName, this.value,
                this.request, this.callerClassFullName,
                this.callerClassSimpleName, this.targetType,
                this.targetGenericTypes, this.implementedInterfaces);
    }

}
