/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pityubak.liberator.config;

import com.pityubak.liberator.data.Interception;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pityubak
 */
public final class ParameterInterceptionHandling {

    private final List<Interception> list = new ArrayList<>();

    public <T> void registrate(Interception interception) {

        this.list.add(interception);
    }

    public <T> T getRequiredArgs(Class<?> targetClass) {

        return (T) this.list.stream()
                .filter(t -> t.getTarget().equals(targetClass))
                .findFirst().orElse(null);
    }

    public List<Interception> getList() {
        return list;
    }

}
