package com.pityubak.liberator.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Pityubak
 */
public class ObjectMapper<W> implements Mapper<W> {

    private final List<W> mappedObjects = new ArrayList<>();

    @Override
    public void createMapping(W details) {
        mappedObjects.add(details);
    }

    @Override
    public List<W> mapping() {
        return Collections.unmodifiableList(mappedObjects);
    }

    @Override
    public void removeMapping() {
        mappedObjects.clear();
    }

}
