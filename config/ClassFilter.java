package com.pityubak.liberator.config;

import com.pityubak.collier.Collier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * @author Pityubak
 */
public class ClassFilter implements Filter {

    private final Collier collier;

    private final List<Class<?>> allClasses = new ArrayList<>();
    private final List<Class<?>> filteredClasses = new ArrayList<>();

    public ClassFilter(Collier collier) {
        this.collier = collier;
        init();
    }

    private void init() {
        Consumer<Class<?>> cons = allClasses::add;
        this.collier.linkToCollector(cons);
    }

    @Override
    public void filter(Class<?>... classes) {
        filteredClasses.addAll(Arrays.asList(classes));
    }

    @Override
    public List<Class<?>> getFinalClassList() {
        return allClasses.stream()
                .filter(t -> !filteredClasses.contains(t))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAllClasses() {
        filteredClasses.clear();
    }
}
