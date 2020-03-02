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
package com.pityubak.liberator.proxy;

import com.pityubak.liberator.misc.Insertion;
import com.pityubak.liberator.misc.ModificationFlag;
import com.pityubak.liberator.service.AbstractMethodHandling;
import java.util.function.Consumer;

/**
 *
 * @author Pityubak
 */
public final class InjectionProxy implements InjectionStream {

    private final Consumer consumer;

    private final AbstractMethodHandling methodHandling;

    public InjectionProxy(Consumer consumer, AbstractMethodHandling methodHandling) {
        this.consumer = consumer;
        this.methodHandling = methodHandling;
    }

    @Override
    public void execute() {
        for (Insertion ins : Insertion.values()) {
            if (ins.isAggregate()) {
                this.methodHandling.execute(ins);
                if (ins.isBeforeState()) {
                    final ModificationFlag flag = ins.getFlag();
                    this.consumer.accept(flag);
                }

            }
        }

    }
}
