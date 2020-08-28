# Liberator
Java injection tool that helps create custom annotation library.

### History

- 0.3.1 submodules-Founder, Collier  
- 0.3 adding basic configuration support
- 0.2 same logic, but Liberator became more robust and flexible
- 0.1 initial version

Next version: error handling and logging

## Overview

Liberator use "method-first" approach, the method determines which annotation belongs to.
So let see, how Liberator works in working example (AutoInjector) :



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
Liberator has two annotation, @MethodBox marks the class, in which method is located and @MethodElement, that marks the method.\
@MethodElement annotation has an enum value(ModificationFlag).\
This enum tells to Liberator, when injection should happen.\
Liberator has four injection phase:Creation, High, Normal and Low.\
Parameter of method always must to be in first place custom annotation \
and in second place Liberator's special class: Response. Response contains some information \
from target of injection(for example:type, value).

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
Main class always must to contain init and inject methods. \
And here's the main class of custom annotation library:

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
Create config class, and set filter and registrate abstraction. This is one of the XMlGrinder's Config files.

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


## Limitations
For now Liberator support only field, class  and limited method injection with static/early initialization. 

## Submodules
1. [Collier](https://github.com/Pityubak/Collier)
2. [Founder](https://github.com/Pityubak//Founder)

## Example
1. [GameAnnotationLibrary-this made by old Liberator version, not updated](https://github.com/Pityubak/GameAnnotationLibrary)
2. [AutoInjector-made by Liberator 0.2 version](https://github.com/Pityubak/AutoInjector)
3. [XmlGrinder-made by Liberator 0.3 version](https://github.com/Pityubak/XmlGrinder)
