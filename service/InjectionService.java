package com.pityubak.liberator.service;

import com.pityubak.founder.Founder;
import com.pityubak.liberator.misc.ModificationFlag;
import java.lang.reflect.Field;
import com.pityubak.liberator.misc.Insertion;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;
import com.pityubak.liberator.builder.MethodDependencyService;

/**
 *
 * @author Pityubak
 */
public class InjectionService implements Injection {

    private final ClassInjectionService inspector;
    private final FieldInjectService fieldService;
    private final Founder founder;
    private final MethodDependencyService methodDependencyService;
    private final AbstractMethodHandling methodHandling;
    private final DetailsService service;
    private final MethodInjectService methodService;

    public InjectionService(Founder founder,
            MethodDependencyService config, DetailsService service,
            AbstractMethodHandling methodHandling) {
        this.founder = founder;
        this.service = service;
        this.methodDependencyService = config;
        this.methodHandling = methodHandling;
        this.methodService = new MethodInjectService(this.service);
        this.inspector = new ClassInjectionService(founder, this.service);
        this.fieldService = new FieldInjectService(founder, this.service);

    }

    @Override
    public void inject(final Object target, final ModificationFlag flag) {

        final List<Class<?>> annotationList = this.methodDependencyService.getAnnotationList(flag);
        final Class<?> injectedClass = target.getClass();
        try {

            if (injectedClass.getDeclaredAnnotations().length > 0) {
                this.inspector.inject(injectedClass, target, annotationList, flag);
            }

            for (Field f : injectedClass.getDeclaredFields()) {

                if (f.getDeclaredAnnotations().length > 0) {
                    this.fieldService.inject(f, target, annotationList, flag);
                }

            }

            for (Method m : injectedClass.getDeclaredMethods()) {

                if (m.getDeclaredAnnotations().length > 0) {
                    this.methodService.inject(m, target, annotationList, flag);
                }
            }

            this.injectAbstractMethod(flag);
        } catch (IllegalArgumentException ex) {
            throw new InjectionException("Injection failed : " + target.getClass().getName() + " : " + ex);
        }

    }

    private void injectAbstractMethod(ModificationFlag flag) {
        Insertion insertion = flag.getOneByOneInsertion();
        Consumer<Insertion> c = this.methodHandling::execute;
        c.accept(insertion);
    }

}
