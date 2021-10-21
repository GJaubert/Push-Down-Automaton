package source.main;

import java.util.*;

public class Delta {

  private Map<Map.Entry<State, Vector<Symbol>>, Set<Map.Entry<State, Vector<Symbol>>>> transitionsMap;

  Delta(Vector<Vector<String>> inputTransitions) {
    transitionsMap = new HashMap<Map.Entry<State, Vector<Symbol>>, Set<Map.Entry<State, Vector<Symbol>>>>();
    for (int i = 0; i < inputTransitions.size(); i++) {
      //Left side of main hash entry
      State tmpState = new State(inputTransitions.get(i).get(0), false);
      Symbol readSymbol = new Symbol(inputTransitions.get(i).get(1));
      Symbol readStackSymbol = new Symbol(inputTransitions.get(i).get(2));
      Vector<Symbol> tmpVector = new Vector<Symbol>();
      tmpVector.add(readSymbol);
      tmpVector.add(readStackSymbol);
      Map.Entry<State, Vector<Symbol>> tmpEntry = new AbstractMap.SimpleEntry<State, Vector<Symbol>>(tmpState, tmpVector);

      //  Right side of main hash entry
      State destinationState = new State(inputTransitions.get(i).get(3), false);
      Vector<Symbol> entryStackSymbols = new Vector<Symbol>();
      for (int j = 4; j < inputTransitions.get(i).size(); j++) {
        Symbol tmpSymbol = new Symbol(inputTransitions.get(i).get(j));
        entryStackSymbols.add(tmpSymbol);
      }
      Map.Entry<State, Vector<Symbol>> rightSideEntry = new AbstractMap.SimpleEntry<State, Vector<Symbol>>(destinationState, entryStackSymbols);

      //  Creating and adding the main hash entry
      if (transitionsMap.get(tmpEntry) == null) {   //No existe esta key
        Set<Map.Entry<State, Vector<Symbol>>> tmpSet = new HashSet<Map.Entry<State, Vector<Symbol>>>();
        tmpSet.add(rightSideEntry);
        transitionsMap.put(tmpEntry, tmpSet);
      } else {    //Ya existe esta key
        Set<Map.Entry<State, Vector<Symbol>>> newSet = transitionsMap.get(tmpEntry);
        newSet.add(rightSideEntry);
        transitionsMap.put(tmpEntry, newSet);
      }
    }
  }

  public Map<Map.Entry<State, Vector<Symbol>>, Set<Map.Entry<State, Vector<Symbol>>>> getTransitionsMap() {
    return transitionsMap;
  }
}
