package source.main;

import java.util.*;

import javax.swing.text.Position;

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
  private State currentState;
  private Delta transitions;
  private Boolean stringAccepted;
  private Stack<Symbol> stack;
  private Vector<Symbol> stackElements;

  Automaton(Set<String> inputStates, Set<String> inputAlphabet, 
            Set<String> inputStackAlphabet, String inputInitialState, 
            String inputInitialStackSymbol, Vector<Vector<String>> inputTransitions) {
        
    stack = new Stack<Symbol>();
    stackElements = new Vector<Symbol>();
    states = new HashSet<State>();
    stringAccepted = false;

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
    currentState = initialState;
    System.out.println(initialState.getName());
    
    initialStackSymbol = new Symbol(inputInitialStackSymbol);
    System.out.println(initialStackSymbol.value);
    stack.push(initialStackSymbol);
    
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

  public void checkString(String inputString) {
    transit(inputString);
    if (stringAccepted) {
      System.out.println("Cadena aceptada");
    } else {
      System.out.println("Esta cadena no pertenece al lenguaje");
    }
  }

  public void transit(String inputString) {
    if (stack.empty()) {
      stringAccepted = true;
      return;
    }
    Vector<Map.Entry<State, Vector<Symbol>>> possibleTransitions = findTransitions(inputString);
    if (possibleTransitions.size() == 0) {
      stringAccepted = false;
      return;
    }
    //aqui debo hacer una foto de la pila;
    Stack<Symbol> stackSnapShot = stack;
    System.out.println(possibleTransitions);
    //for (int i = 0; i < possibleTransitions.size(); i++) {
    //  stack = stackSnapShop;
    //  executeTransitions(inputString, possibleTransitions);
    //}
  }

  //En este método falta que busque las entradas para .
  public Vector<Map.Entry<State, Vector<Symbol>>> findTransitions(String inputString) {
    Vector<Map.Entry<State, Vector<Symbol>>> possibleTransitions = new Vector<Map.Entry<State, Vector<Symbol>>>();
    Vector<Symbol> inputSymbols = new Vector<Symbol>();
    inputSymbols.add(new Symbol(String.valueOf(inputString.charAt(0))));    //Primer caracter de la cadena
    inputSymbols.add(stack.peek());   //Primer elemento de la pila
    Map.Entry<State, Vector<Symbol>> deltaMapKey = new AbstractMap.SimpleEntry<State, Vector<Symbol>>(currentState, inputSymbols);
    Set<Map.Entry<State, Vector<Symbol>>> destinationsSet = transitions.getTransitionsMap().get(deltaMapKey);
    if (destinationsSet != null) {
      Iterator<Map.Entry<State, Vector<Symbol>>> itr = destinationsSet.iterator();
      while (itr.hasNext()) {
        possibleTransitions.add(itr.next());
      }
    }
    return possibleTransitions;
  }

  public void executeTransitions(String inputString, Vector<Map.Entry<State, Vector<Symbol>>> possibleTransitions) {

  }
}