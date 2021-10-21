package source.main;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class Alphabet {
  private Set<Symbol> symbols;

  Alphabet(Set<String> inputSymbols) {
    symbols = new HashSet<Symbol>();
    Iterator<String> iter = inputSymbols.iterator();
    while (iter.hasNext()) {
      String symbol = iter.next();
      symbols.add(new Symbol(symbol));
    }
  }
  
  public Set<Symbol> getSymbols() {
    return symbols;
  }
}
