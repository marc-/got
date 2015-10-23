package org.github.got;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.github.got.Context.Scope;

/**
 * {@link org.github.got.Command} metadata.
 *
 * @author Maksim Chizhov
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface CommandA {

  /** Template for used input. */
  String[] value();

  /** Scope indicates when command can be used. */
  Scope[] scope() default Scope.GLOBAL;

  /**
   * Indicates, that command in case if successful execution changes game phase.
   */
  Scope starts() default Scope.NONE;

  /** Run command only once. */
  boolean runOnce() default false;
}
