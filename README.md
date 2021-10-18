# Liberator
Java injection tool that helps create custom annotation library.

### History

- 0.3.1 submodules-Founder, Collier  
- 0.3 adding basic configuration support
- 0.2 same logic, but Liberator became more robust and flexible
- 0.1 initial version

Next version: error handling and logging

## Overview

Liberator uses a "method-first" approach, the method determines which annotation it belongs to. So let's see how Liberator works in a working example (AutoInjector) :



### First step:
Custom annotation creation :

~~~java

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {
    
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR,ElementType.FIELD})
public @interface AutoInject {
    Class<?>[] value() default {};
}
~~~


### Second step:
The Liberator has two annotations, @MethodBox denotes the class in which the method is located, and @MethodElement denotes the method.
The @MethodElement annotation has an enum value (ModificationFlag).\
This enum tells Liberator when the injection should take place.\
Liberator has four injection phases: creation, high, normal and low.\
The method parameter should always be the first in the custom annotations
and in the second place the special class of Liberator: Response. The response contains some information \
about the target of the injection (e.g. type, value).


~~~java

@MethodBox
public class AutoInjectorService {

    private final Map<String, Class<?>> map = new HashMap<>();

    @MethodElement
    public void mapInterface(Service service, Response response) {
        //information from target through response's getters
        final Class<?> type = response.getTargetType();
        final String injInterface = type.getInterfaces()[0].getSimpleName();
        this.map.put(injInterface, type);

    }

    @MethodElement(ModificationFlag.PRIORITY_HIGH)
    public <T> T getValue(AutoInject inject, Response response) {
        //Request is a special class from Liberator, 
        //it create new instance of any class
        final Request request = response.getRequest();
        Class type;
        if (inject.value().length < 1) {
            final Class<?> injType = response.getTargetType();
            type = this.map.get(injType.getSimpleName());
        } else {
            type = inject.value()[0];
        }
        if (type == null) {
            throw new NullPointerException("Class must be annotated with @Service or add explicit classname in @AutoInject annotation ");
        }
        request.setRequestType(type);

        return (T) request.response(response.getTargetName());
    }
}

~~~

### Third step:
The main class should always contain init and inject methods. \
And here is the main class of the custom annotation library:

~~~java
public class Context {

    private final List<Class<?>> classes;
    private final Liberator liberator = new Liberator(Context.class);

    public Context(Class<?> mainClass) {
        this.classes = this.liberator.getClassListFromTargetPackage(mainClass);
    }

    public <W> W inject(Class<?> type) {
        final Request request = this.liberator.askForRequest();
        request.setRequestType(type);
        //always required
        this.liberator.init();
        final Map<String, Class<?>> classList = new HashMap<>();
        classes.forEach((cl) -> {
            if (!cl.isInterface()) {
                classList.put(cl.getSimpleName(), cl);
            }
        });
        //always required
        this.liberator.inject(classList);
        return (W) request.response(type.getSimpleName());
    }
}
~~~

### Fourth(optional) step:
Create a config class and set the filter and registration abstraction. This is one of the config files in XMlGrinder.

~~~java
@Config(XmlWrite.class)
public class XmlWriteConfig implements ConfigurationService {

    @Override
    public void filter(CollectionConfigurationLayer collection) {
        ConfigurationService.super.filter(collection); 
        
        collection.filter(XmlWriteScoutService.class, XmlReadService.class, XmlReadScoutService.class);
        
    }
    
    @Override
    public void registerAbstractMethod(MethodConfigurationLayer handler) {
        ConfigurationService.super.registerAbstractMethod(handler); 

        handler
                .registrate(XmlWriteService.class, Writeable.class, Insertion.AFTER_LOW)
                .registrate(XmlWriteService.class, Establishing.class, Insertion.PER_CLASS_HIGH)
                .registrate(XmlWriteService.class, CounterResetable.class, Insertion.BEFORE_NORMAL)
                .registrate(XmlWriteService.class, NodeCountable.class, Insertion.PER_CLASS_NORMAL);
    }
    
}
~~~



## Submodules
1. [Collier](https://github.com/Pityubak/Collier)
2. [Founder](https://github.com/Pityubak//Founder)

## Example
1. [GameAnnotationLibrary-this made by old Liberator version, not updated](https://github.com/Pityubak/GameAnnotationLibrary)
2. [AutoInjector-made by Liberator 0.2 version](https://github.com/Pityubak/AutoInjector)
3. [XmlGrinder-made by Liberator 0.3 version](https://github.com/Pityubak/XmlGrinder)
