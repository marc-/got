package org.github.got;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.github.got.Context.Scope;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface CommandA {

  String[] value();

  Scope[] scope() default Scope.GLOBAL;

  Scope starts() default Scope.NONE;

  boolean runOnce() default false;
}
