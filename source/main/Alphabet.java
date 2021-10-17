package source.main;

import java.util.Set;

public class Alphabet {
  private Set<Character> symbols;

  Alphabet(Set<Character> inputSymbols) {
    symbols = inputSymbols;
  }
  
  public Set<Character> getSymbols() {
    return symbols;
  }
}
