package com.pityubak.liberator.annotations;

import com.pityubak.liberator.misc.ModificationFlag;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Pityubak
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodElement {


    ModificationFlag value() default ModificationFlag.PRIORITY_CREATION;

}
