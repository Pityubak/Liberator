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
