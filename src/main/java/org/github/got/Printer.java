package org.github.got;

/**
 * Basic printer interface open for extensions (different terminal types, for
 * instance).
 *
 * @author Maksim Chizhov
 *
 */
public interface Printer {

  void print(PrintContext ctx);
}
