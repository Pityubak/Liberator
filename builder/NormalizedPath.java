/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pityubak.liberator.builder;

/**
 *
 * @author Pityubak
 */
public final class NormalizedPath {

    private String rawPath;

    public NormalizedPath(final String rawPath) {
        this.rawPath = rawPath;
    }

    public String getNormalizedPath() {
        if (rawPath.startsWith("jar:")) {
            rawPath = rawPath.substring(4);
        }
        if (rawPath.startsWith("file:")) {
            rawPath = rawPath.substring(5);

        }
        int position = rawPath.indexOf("!/");

        rawPath = position == -1 ? rawPath : rawPath.substring(0, position);
        return rawPath;
    }
}
