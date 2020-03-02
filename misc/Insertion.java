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
 */
public enum Insertion implements EnumInsertionReference {
    BEFORE_CREATION {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.PRIORITY_CREATION;
        }

        @Override
        public boolean isAggregate() {
            return true;
        }

        @Override
        public boolean isBeforeState() {
            return true;
        }

    },
    AFTER_CREATION {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.PRIORITY_CREATION;
        }

        @Override
        public boolean isAggregate() {
            return true;
        }

        @Override
        public boolean isBeforeState() {
            return false;
        }

    },
    BEFORE_HIGH {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.PRIORITY_HIGH;
        }

        @Override
        public boolean isAggregate() {
            return true;
        }

        @Override
        public boolean isBeforeState() {
            return true;
        }

    },
    AFTER_HIGH {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.PRIORITY_HIGH;
        }

        @Override
        public boolean isAggregate() {
            return true;
        }

        @Override
        public boolean isBeforeState() {
            return false;
        }

    },
    BEFORE_NORMAL {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.PRIORITY_NORMAL;
        }

        @Override
        public boolean isAggregate() {
            return true;
        }

        @Override
        public boolean isBeforeState() {
            return true;
        }

    },
    AFTER_NORMAL {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.PRIORITY_NORMAL;
        }

        @Override
        public boolean isAggregate() {
            return true;
        }

        @Override
        public boolean isBeforeState() {
            return false;
        }

    },
    BEFORE_LOW {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.PRIORITY_LOW;
        }

        @Override
        public boolean isAggregate() {
            return true;
        }

        @Override
        public boolean isBeforeState() {
            return true;
        }

    },
    AFTER_LOW {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.PRIORITY_LOW;
        }

        @Override
        public boolean isAggregate() {
            return true;
        }

        @Override
        public boolean isBeforeState() {
            return false;
        }

    },
    PER_CLASS_CREATION {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.NONE;
        }

        @Override
        public boolean isAggregate() {
            return false;
        }

        @Override
        public boolean isBeforeState() {
            return false;
        }

    },
    PER_CLASS_HIGH {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.NONE;
        }

        @Override
        public boolean isAggregate() {
            return false;
        }

        @Override
        public boolean isBeforeState() {
            return false;
        }

    },
    PER_CLASS_NORMAL {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.NONE;
        }

        @Override
        public boolean isAggregate() {
            return false;
        }

        @Override
        public boolean isBeforeState() {
            return false;
        }

    },
    PER_CLASS_LOW {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.NONE;
        }

        @Override
        public boolean isAggregate() {
            return false;
        }

        @Override
        public boolean isBeforeState() {
            return false;
        }

    },
    NONE {
        @Override
        public ModificationFlag getFlag() {
            return ModificationFlag.NONE;
        }

        @Override
        public boolean isAggregate() {
            return false;
        }

        @Override
        public boolean isBeforeState() {
            return false;
        }

    };

}
