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
package com.pityubak.liberator.misc;

/**
 *
 * @author Pityubak
 * @since 2019.10.20
 * @version 1.1
 */
public enum ModificationFlag implements EnumFlagReference {
    PRIORITY_CREATION {
        @Override
        public Insertion getOneByOneInsertion() {
            return Insertion.PER_CLASS_CREATION;
        }

        @Override
        public Insertion getaggregateInsertion() {
            return Insertion.BEFORE_CREATION;
        }

    },
    PRIORITY_HIGH {
        @Override
        public Insertion getOneByOneInsertion() {
            return Insertion.PER_CLASS_HIGH;
        }

        @Override
        public Insertion getaggregateInsertion() {
            return Insertion.BEFORE_HIGH;
        }
    },
    PRIORITY_NORMAL {
        @Override
        public Insertion getOneByOneInsertion() {
            return Insertion.PER_CLASS_NORMAL;
        }

        @Override
        public Insertion getaggregateInsertion() {
            return Insertion.BEFORE_NORMAL;
        }
    },
    PRIORITY_LOW {
        @Override
        public Insertion getOneByOneInsertion() {
            return Insertion.PER_CLASS_LOW;
        }

        @Override
        public Insertion getaggregateInsertion() {
            return Insertion.BEFORE_LOW;
        }

    },
    NONE {
        @Override
        public Insertion getOneByOneInsertion() {
            return Insertion.NONE;
        }

        @Override
        public Insertion getaggregateInsertion() {
            return Insertion.NONE;
        }

    }

}
