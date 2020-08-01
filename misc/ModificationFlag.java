package com.pityubak.liberator.misc;

/**
 *
 * @author Pityubak
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
