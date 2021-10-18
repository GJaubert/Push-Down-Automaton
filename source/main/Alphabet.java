package source.main;

import java.util.Set;

public class Alphabet {
  private Set<String> symbols;

  Alphabet(Set<String> inputSymbols) {
    symbols = inputSymbols;
  }
  
  public Set<String> getSymbols() {
    return symbols;
  }
}
