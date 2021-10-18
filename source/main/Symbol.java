package source.main;

import java.util.Objects;

public class Symbol {
  public String value;

  Symbol(String inputValue) {
    value = inputValue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if(obj == null || obj.getClass()!= this.getClass())
      return false;

    Symbol state = (Symbol) obj;

    return (state.value.equals(this.value));
  }
}
