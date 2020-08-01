package com.pityubak.liberator.config;

import com.pityubak.liberator.misc.ModificationFlag;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 *
 * @author Pityubak
 */
public final class MethodDependencyService implements MethodDependency {

    private final List<MethodDetails> methodDetailsList = new ArrayList<>();

    @Override
    public void createMethodMapping(final MethodDetails details) {
        methodDetailsList.add(details);
    }

    @Override
    public MethodDetails methodMapping(final Class<?> cl, final ModificationFlag flag) {
       
        final MethodDetails base = this.get(flag).stream()
                .filter(x -> x.getAnnotation().equals(cl))
                .findFirst()
                .orElse(null);

        if (base == null) {
            throw new NoSuchElementException("This class " + cl + " is not registered!");
        }

        return base;
    }

    private List<MethodDetails> get(final ModificationFlag flag) {

        final List<MethodDetails> list = new ArrayList<>();
        this.methodDetailsList.forEach(el -> {
            if (el.getModFlag().equals(flag)) {
                list.add(el);

            }
        });
        return list;
    }

    @Override
    public List<Class<?>> getAnnotationList(final ModificationFlag flag) {

        return get(flag).stream().map(MethodDetails::getAnnotation).collect(Collectors.toList());
    }

    @Override
    public void removeMapping() {
        this.methodDetailsList.clear();
    }

}
