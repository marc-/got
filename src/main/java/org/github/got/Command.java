package org.github.got;

public interface Command {

  static String INIT = "__init__";

  void issue(Context context);

}
