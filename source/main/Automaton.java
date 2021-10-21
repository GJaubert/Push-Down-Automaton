package source.main;

import java.util.*;

public class Automaton {

  private final String TRACE_SYMBOL = "&";
  private final String PRINT_PROPORTIONS = "%-7s | %-18s | %-12s | %-15s | %-9s";
  private Set<State> states;
  private Alphabet alphabet;
  private Alphabet stackAlphabet;
  private State initialState;
  private Symbol initialStackSymbol;
  private State finalState;
  //private State currentState;
  private Delta transitions;
  private Boolean stringAccepted;
  private Stack<Symbol> stack;

  Automaton(Set<String> inputStates, Set<String> inputAlphabet, 
            Set<String> inputStackAlphabet, String inputInitialState, 
            String inputInitialStackSymbol, Vector<Vector<String>> inputTransitions) {
        
    stack = new Stack<Symbol>();
    states = new HashSet<State>();
    stringAccepted = false;

    for (String element: inputStates) {
      State tmpState = new State(element, false);
      states.add(tmpState);
    }
    //System.out.println(states.size());
    
    alphabet = new Alphabet(inputAlphabet);
    //System.out.println(alphabet.getSymbols().size());
    
    stackAlphabet = new Alphabet(inputStackAlphabet);
    //System.out.println(stackAlphabet.getSymbols().size());
    
    initialState = new State(inputInitialState, false);
    //currentState = initialState;
    //System.out.println(initialState.getName());
    
    initialStackSymbol = new Symbol(inputInitialStackSymbol);
    //System.out.println(initialStackSymbol.value);
    //stackElements.addFirst(stack.peek());
    
    transitions = new Delta(inputTransitions);

  //  try {
      Boolean isValid = validateAutomaton();
      if (!isValid) throw new RuntimeException("Automaton not valid");
   // } catch (Exception error) {
      //System.out.println(error);
  //  }
    
    //Probando si funciona
    Vector<Symbol> keySymbols = new Vector<Symbol>();
    keySymbols.add(new Symbol("a"));
    keySymbols.add(new Symbol("A"));
    //Map.Entry<State, Vector<Symbol>> testMapEntry = new AbstractMap.SimpleEntry<State, Vector<Symbol>>(initialState, keySymbols);
    //System.out.println(transitions.getTransitionsMap().get(mapEntry).iterator().next());

    // Iterator<Map.Entry<State, Vector<Symbol>>> iter = transitions.getTransitionsMap().get(testMapEntry).iterator();
    // while (iter.hasNext()) {
    //   Map.Entry<State, Vector<Symbol>> tmpEntry = iter.next();
    //   System.out.println("hola");
    //   System.out.println(tmpEntry.getKey().getName() + " " + tmpEntry.getValue().get(0).value + " " + tmpEntry.getValue().get(1).value);
    // }
  }

  public void checkString(String inputString) {
    printTitle();
    stringAccepted = false;
    stack.removeAllElements();
    stack.push(initialStackSymbol);
    transit(inputString, initialState);
    if (stringAccepted) {
      System.out.println("Cadena aceptada ✅");
    } else {
      System.out.println("Esta cadena no pertenece al lenguaje ❌");
    }
  }

  private void transit(String inputString, State currentState) {
    if (inputString.length() == 0) {
      if (stack.empty()) {
        stringAccepted = true;
      }
    }
    if (stack.empty()) {
      return;
    }
    //System.out.println(stack.peek().value);
    Vector<Map.Entry<State, Vector<Symbol>>> possibleTransitions = findTransitions(inputString, currentState);
    if (possibleTransitions.size() == 0) {
      return;
    }
    //aqui debo hacer una foto de la pila;
    Stack<Symbol> stackSnapShot = new Stack<Symbol>();
    stackSnapShot.addAll(stack);
    //LinkedList<Symbol> stackElementsCopy = stackElements;
    //System.out.println(possibleTransitions);
    for (int i = 0; i < possibleTransitions.size(); i++) {
      stack.removeAllElements();
      stack.addAll(stackSnapShot);
      //System.out.println(possibleTransitions.size());
      printTransition(inputString, possibleTransitions.get(i), currentState, stackSnapShot);
      executeTransition(inputString, possibleTransitions.get(i));
      if (stringAccepted == true) break;
    }
  }

  //En este método falta que busque las entradas para .
  private Vector<Map.Entry<State, Vector<Symbol>>> findTransitions(String inputString, State currentState) {
    Vector<Map.Entry<State, Vector<Symbol>>> possibleTransitions = new Vector<Map.Entry<State, Vector<Symbol>>>();
    Vector<Symbol> inputSymbols = new Vector<Symbol>();
    inputSymbols.add(new Symbol("."));
    inputSymbols.add(stack.peek());   //Primer elemento de la pila
    Map.Entry<State, Vector<Symbol>> epsilonMapKey = new AbstractMap.SimpleEntry<State, Vector<Symbol>>(currentState, inputSymbols);
    Set<Map.Entry<State, Vector<Symbol>>> epsilonSet = transitions.getTransitionsMap().get(epsilonMapKey);
    if (epsilonSet != null) {
      Iterator<Map.Entry<State, Vector<Symbol>>> itr = epsilonSet.iterator();
      while (itr.hasNext()) {
        Map.Entry<State, Vector<Symbol>> tmp = itr.next();
        if (!tmp.getValue().get(tmp.getValue().size() - 1).value.equals(TRACE_SYMBOL)) {
          tmp.getValue().add(new Symbol(TRACE_SYMBOL));    //Caracter reservado para saber si es una transicion que no consume caracter
        }
        possibleTransitions.add(tmp);
      }
    }
    if (inputString.length() > 0) {
      inputSymbols.set(0, new Symbol(String.valueOf(inputString.charAt(0))));    //Primer caracter de la cadena
      //System.out.println("probando con " + String.valueOf(inputString.charAt(0)) + " " + stack.peek().value);
      Map.Entry<State, Vector<Symbol>> deltaMapKey = new AbstractMap.SimpleEntry<State, Vector<Symbol>>(currentState, inputSymbols);
      Set<Map.Entry<State, Vector<Symbol>>> destinationsSet = transitions.getTransitionsMap().get(deltaMapKey);
      if (destinationsSet != null) {
        Iterator<Map.Entry<State, Vector<Symbol>>> itr = destinationsSet.iterator();
        while (itr.hasNext()) {
          possibleTransitions.add(itr.next());
        }
      }
    }
    return possibleTransitions;
  }

  private void executeTransition(String inputString, Map.Entry<State, Vector<Symbol>> transition) {
    String newString = inputString;
    Vector<Symbol> stackSymbols = transition.getValue();
    if (stackSymbols.get(stackSymbols.size() - 1).value.equals(TRACE_SYMBOL)) {
    } else {
      newString = newString.substring(1);
    }
    stack.pop();
    for (int i = stackSymbols.size() - 1; i >= 0; i--) {
      if (stackSymbols.get(i).value.equals(".") || stackSymbols.get(i).value.equals(TRACE_SYMBOL)) continue;
      stack.push(stackSymbols.get(i));
    }
    transit(newString, transition.getKey());
  }

  private void printTransition(String inputString, Map.Entry<State, Vector<Symbol>> transition, State currentState, Stack<Symbol> stackSnapShot) {
    String consumedSymbol;
    if (inputString.length() > 0 && transition.getValue().get(transition.getValue().size() - 1).value != TRACE_SYMBOL) {
      consumedSymbol = String.valueOf(inputString.charAt(0));
    } else {
      consumedSymbol = ".";
    }
    System.out.printf(PRINT_PROPORTIONS + " ", currentState.getName(), consumedSymbol, inputString, stack.peek().value, transition.getKey().getName());
    for (int i = 0; i < transition.getValue().size(); i++) {
      if (transition.getValue().get(i).value.equals(TRACE_SYMBOL)) continue;
      System.out.print(transition.getValue().get(i).value);
    }
    System.out.print("\n");
  }

  private void printTitle() {
    System.out.printf(PRINT_PROPORTIONS + "\n", "estado", "simbolo consumido", "cadena", "top de la pila", "transición");
  }

  private Boolean validateAutomaton() {
    if (!states.contains(initialState)) return false;
    if (!stackAlphabet.getSymbols().contains(initialStackSymbol)) return false;
    
    return true; 
  }
}