
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

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pityubak
 * @since 2019.9.20
 * @see InstanceCollection
 * @version 1.0 It collect all annotations, what are annotated with Register and
 * all classes and methods, where MethodBox/MethodElement annotations
 * are present.
 */
public final class ClassInstanceCollection implements InstanceCollection {

    private final Data classData;
    private final Class<?> entryClass;

    public ClassInstanceCollection(final Data finder,final Class<?> entryClass) {
        this.classData = finder;
        this.entryClass = entryClass;

    }



    /**
     * @return List<Class<?>>
     * 
     */
    @Override
    public List<Class<?>> collect() {

        /*With instance of Data iterate over all files in
          target project and call registerDependencyObject
          method.
         */
        List<Class<?>> classList = new ArrayList<>();
        String packageName = this.entryClass.getPackage().getName();
        classData.getPathList().stream().map(Path::toString)
                .filter(fileName -> fileName.endsWith(".class"))
                .map(fileName -> fileName.replaceAll(".class", ""))
                .forEachOrdered((String file) -> {
                    String[] currentFile = file.split(packageName);
                    String neededPart = currentFile[currentFile.length - 1];
                    neededPart = neededPart.replace("\\", ".");
                    try {
                        Class<?> loadedClass = Class.forName(packageName + neededPart);
                        classList.add(loadedClass);

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ClassInstanceCollection.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
        return classList;
    }

}
