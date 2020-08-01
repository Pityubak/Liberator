package com.pityubak.liberator.service;

import com.pityubak.founder.Founder;
import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.data.Response;
import com.pityubak.liberator.proxy.BuilderProxy;
import com.pityubak.liberator.proxy.ResponseProxy;
import com.pityubak.liberator.proxy.VirtualResponse;
import java.util.Arrays;

/**
 *
 * @author Pityubak
 */
public final class ClassResponseService implements ResponseProxy {

    private final Founder founder;

    private final Object caller;

    private final Class targetClass;

    public ClassResponseService(Founder founder, Object caller, Class targetClass) {
        this.founder = founder;
        this.caller = caller;
        this.targetClass = targetClass;
    }

    @Override
    public Response create() {

        return BuilderProxy.of(VirtualResponse::new)
                .with(VirtualResponse::setTargetName, this.targetClass.getName())
                .with(VirtualResponse::setValue, this.targetClass)
                .with(VirtualResponse::setRequest, new Request(founder))
                .with(VirtualResponse::setCallerClassFullName, this.caller.toString())
                .with(VirtualResponse::setCallerClassSimpleName, caller.getClass().getSimpleName())
                .with(VirtualResponse::setTargetType, this.targetClass)
                .with(VirtualResponse::setImplementedInterfaces, Arrays.asList(this.targetClass.getInterfaces()))
                .build()
                .create();

    }
}
