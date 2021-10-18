package source.main;

import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if(obj == null || obj.getClass()!= this.getClass())
      return false;

    State state = (State) obj;

    return (state.name_.equals(this.name_));
  }

  @Override
  public int hashCode() {
    return Objects.hash(name_);
  }
}
