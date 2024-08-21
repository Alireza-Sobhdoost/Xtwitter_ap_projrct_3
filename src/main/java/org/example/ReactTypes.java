package org.example;

public enum ReactTypes {
    Like {
        @Override
        public String toString() {
            return "Like";
        }
    },
    Save {
        @Override
        public String toString() {
            return "Save";
        }
    },
    UnLike {
        @Override
        public String toString() {
            return "UnLike";
        }
    },
    UnSave {
        @Override
        public String toString() {
            return "UnSave";
        }
    }
}