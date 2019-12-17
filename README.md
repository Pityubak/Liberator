# Liberator
Java injection tool that helps create custom annotation lib.

## Overview

### First step:
Custom annotation registration with @Register annotation:

~~~java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Register
public @interface Observeable {

    String value();

}
~~~


### Second step:
Creating of method, that defines what annotation do:

~~~java

@MethodBox
public class ObservingService {

    @MethodElement(value = MethodFlag.WITH_OBSERVER_AND_VALUE, flag = ModificationFlag.PRIORITY_HIGH)
    public <T> void addObserver(Observeable obs, ObserverService observer, T object) {
        StoredData data = new StoredData(object.getClass(), object);
        observer.registrationWithStoredData(obs.value(), data);
    }

~~~

### Third step:
Creating of entrypoint, where inject classes with custom annotation.
~~~java
   public SwingBuilder(Class<?>[] classes) {
        liberator = new Liberator(SwingBuilder.class);
        this.liberator.inject(classes);

    }
~~~

## Limitations
For now Liberator support only field and class injection.

## Example
1. [GameAnnotationLibrary](https://github.com/Pityubak/GameAnnotationLibrary)

