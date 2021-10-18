package source.main;

import java.util.*;
import source.main.Delta;
import source.main.Alphabet;
import source.main.State;

public class Automaton {

  private Set<State> states;
  private Alphabet alphabet;
  private Alphabet stackAlphabet;
  private State initialState;
  private Symbol initialStackSymbol;
  private State finalState;
  private Delta transitions;

  Automaton(Set<String> inputStates, Set<String> inputAlphabet, 
            Set<String> inputStackAlphabet, String inputInitialState, 
            String inputInitialStackSymbol, Vector<Vector<String>> inputTransitions) {
    
    states = new HashSet<State>();
    for (String element: inputStates) {
      State tmpState = new State(element, false);
      states.add(tmpState);
    }
    System.out.println(states.size());

    alphabet = new Alphabet(inputAlphabet);
    System.out.println(alphabet.getSymbols().size());

    stackAlphabet = new Alphabet(inputStackAlphabet);
    System.out.println(stackAlphabet.getSymbols().size());

    initialState = new State(inputInitialState, false);
    System.out.println(initialState.getName());

    initialStackSymbol = new Symbol(inputInitialStackSymbol);
    System.out.println(initialStackSymbol.value);

    transitions = new Delta(inputTransitions);

    //Probando si funciona
    Vector<Symbol> keySymbols = new Vector<Symbol>();
    keySymbols.add(new Symbol("a"));
    keySymbols.add(new Symbol("A"));
    Map.Entry<State, Vector<Symbol>> testMapEntry = new AbstractMap.SimpleEntry<State, Vector<Symbol>>(initialState, keySymbols);
    //System.out.println(transitions.getTransitionsMap().get(mapEntry).iterator().next());

    Iterator<Map.Entry<State, Vector<Symbol>>> iter = transitions.getTransitionsMap().get(testMapEntry).iterator();
    while (iter.hasNext()) {
      Map.Entry<State, Vector<Symbol>> tmpEntry = iter.next();
      System.out.println(tmpEntry.getKey().getName() + " " + tmpEntry.getValue().get(0).value + " " + tmpEntry.getValue().get(1).value);
    }
  }
}