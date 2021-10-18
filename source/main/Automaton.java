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
    System.out.println(initialState.hashCode());

    initialStackSymbol = new Symbol(inputInitialStackSymbol);
    System.out.println(initialStackSymbol.value);

    transitions = new Delta(inputTransitions);
    Vector<Symbol> keySymbols = new Vector<Symbol>();
    keySymbols.add(new Symbol("a"));
    keySymbols.add(initialStackSymbol);
    Map.Entry<State, Vector<Symbol>> mapEntry = new AbstractMap.SimpleEntry<State, Vector<Symbol>>(initialState, keySymbols);
    //System.out.println(transitions.getTransitionsMap().get(mapEntry).iterator().next());

    Iterator<Map.Entry<State, Vector<Symbol>>> iter = transitions.getTransitionsMap().get(mapEntry).iterator();
    while (iter.hasNext()) {
      Map.Entry<State, Vector<Symbol>> tmpEntry = iter.next();
      System.out.println(tmpEntry.getKey().getName());
    }
    //System.out.println(initialStackSymbol.value);
    //Map<State, Symbol> testMap = new HashMap<State, Symbol>();
    // State state1 = new State("q1", false);
    // State state2 = new State("q1", false);
    // testMap.put(state1, new Symbol("a"));
    // System.out.println(testMap.get(state2));
    // System.out.println(state1.hashCode() + " " + state2.hashCode());
    //transitions.getTransitionsMap().forEach((key, value) -> System.out.println(key + " : " + value));
  }
}