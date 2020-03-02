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
package com.pityubak.liberator.builder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Pityubak
 * @since 2019.12.12
 * @version 1.0 Starting check: is it in jar, then normailze path uri and
 * collect files to list
 *
 */
public final class StartupData implements Data {

    private String rawPath;

    private final boolean inJar;

    public StartupData(final String rawPath) {
        this.rawPath = rawPath;

        this.inJar = startInJar();
    }

    public String normalizePathURI() {

        if (inJar) {
            this.rawPath = new NormalizedPath(this.rawPath).getNormalizedPath();

        }
        return inJar ? new File(rawPath).getPath() : new File(rawPath).getParent();
    }

    boolean startInJar() {
        return this.rawPath.contains(".jar!");
    }

    @Override
    public List<Path> getPathList() {
        final String target = this.normalizePathURI();
        final List<Path> path = new ArrayList<>();
        if (inJar) {
            getFilesFromJar(target, path);
        } else {
            getFilesFromFileSystem(target, path);
        }
        return path;
    }

    private void getFilesFromJar(final String targetPath, final List<Path> list) {
        try {
            try (JarFile jarFile = new JarFile(targetPath)) {
                final Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    final JarEntry entry = entries.nextElement();
                    final String entryName = entry.getName();
                    list.add(Paths.get(targetPath + entryName));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(StartupData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getFilesFromFileSystem(final String classPath, final List<Path> paths) {

        try (Stream<Path> list = Files.find(Paths.get(classPath),
                Integer.MAX_VALUE,
                (filePath, fileAttr) -> fileAttr.isRegularFile())) {

            list.forEach(paths::add);
        } catch (IOException ex) {
            Logger.getLogger(StartupData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
