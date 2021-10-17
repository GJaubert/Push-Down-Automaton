package source.main;

public class State {
  private boolean final_ = false;
  private String name_;

  State(String name, boolean inputFinal) {
    name_ = name;
    final_ = inputFinal;
  }

  public boolean isFinal() {
    return final_;
  }

  public String getName() {
    return name_;
  }
}
