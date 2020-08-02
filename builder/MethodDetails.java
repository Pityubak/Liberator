package com.pityubak.liberator.builder;

import com.pityubak.liberator.misc.ModificationFlag;
import java.util.List;

/**
 *
 * @author Pityubak
 */
public final class MethodDetails {

    private final String methodName;

    private final Class<?> concreteClass;

    private final Class<?> annotation;

    private final List<Class<?>> params;

    private final ModificationFlag modFlag;

    private final List<String> fieldNames;

    private final String simpleName;

    private MethodDetails(Builder builder) {
        this.methodName = builder.methodName;
        this.concreteClass = builder.className;
        this.annotation = builder.annotation;
        this.params = builder.params;
        this.modFlag = builder.modFlag;
        this.fieldNames = builder.fieldNames;
        this.simpleName = builder.simpleName;
    }

    public String getSimpleName() {
        return simpleName;
    }
    

    public String getMethodName() {
        return methodName;
    }

    public Class<?> getConcreteClass() {
        return concreteClass;
    }

    public Class<?> getAnnotation() {
        return annotation;
    }

    public List<Class<?>> getParams() {
        return params;
    }

    public ModificationFlag getModFlag() {
        return modFlag;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    @Override
    public String toString() {
        return "MethodDetails{" + "methodName=" + methodName + ", concreteClass=" + concreteClass + ", annotation=" + annotation + ", params=" + params + ", modFlag=" + modFlag + ", fieldNames=" + fieldNames + ", simpleName=" + simpleName + '}';
    }
    

    public static class Builder {

        private String methodName;

        private Class<?> className;

        private Class<?> annotation;

        private List<Class<?>> params;

        private ModificationFlag modFlag;

        private List<String> fieldNames;

        private String simpleName;

        public Builder withFieldNames(List<String> fieldNames) {
            this.fieldNames = fieldNames;
            return this;
        }

        public Builder addMethodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder addClassName(Class<?> className) {
            this.className = className;
            return this;
        }

        public Builder withAnnotation(Class<?> annotation) {
            this.annotation = annotation;
            return this;
        }

        public Builder withParams(Class<?>[] params) {
            this.params = List.of(params);
            return this;
        }

        public Builder withModificationFlag(ModificationFlag modFlag) {
            this.modFlag = modFlag;
            return this;
        }

        public Builder withSimpleName(String simpleName) {
            this.simpleName = simpleName;
            return this;
        }

        public MethodDetails build() {
            return new MethodDetails(this);
        }

    }

}
