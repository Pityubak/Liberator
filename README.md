# Liberator
Java injection tool that helps create custom annotation library.

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
Liberator has two annotation, @MethodBox marks the class, in wich method is located\
and @MethodElement, that marks the method. @MethodElement annotation has an enum value(ModificationFlag).\
This enum tells to Liberator, when injection should happen.\
Liberator has four injection phase:Creation, High, Normal and Low.\
Parameter of method always must to be in first place our annotation \
and in second place Liberator's special class: Response. Response contains some information \
from target of injection(for example:type, value).

~~~java

@MethodBox
public class AutoInjectorService {

    private final Map<String, Class<?>> map = new HashMap<>();

    @MethodElement
    public <T> void mapInterface(Service service, Response response) {
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

This is basic operations from Liberator, but it can inject some abstract method. \
Insertion  is possible before and after each phase, or each class. Note:abstract method's type must to be \
void without parameter.
~~~java
   //Parameters'order is concrete class, interface, and Insertion enum
   this.liberator.registerAbstractMethod(XmlWriteScoutService.class, Scout.class, Insertion.AFTER_LOW);
~~~


## Limitations
For now Liberator support only field, class  and limited method injection with static/early initialization. 

## Example
1. [GameAnnotationLibrary-this made by old Liberator version, not updated](https://github.com/Pityubak/GameAnnotationLibrary)
2. [AutoInjector-made by Liberator 0.2 version](https://github.com/Pityubak/AutoInjector)
3. [XmlGrinder-made by Liberator 0.2 version](https://github.com/Pityubak/XmlGrinder)
