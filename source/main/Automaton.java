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
  private Set<State> finalState;
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
    
    alphabet = new Alphabet(inputAlphabet);
    
    stackAlphabet = new Alphabet(inputStackAlphabet);
    
    initialState = new State(inputInitialState, false);
    
    initialStackSymbol = new Symbol(inputInitialStackSymbol);
    
    transitions = new Delta(inputTransitions);

    Boolean isValid = validateAutomaton();
    if (!isValid) throw new RuntimeException("Automaton not valid");
  }

  //  Funcion que dada una cadena comprueba si es aceptada por el lenguaje que reconoce el autómata 
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

  //  Función recursiva que sirve para transitar entre estados
  private void transit(String inputString, State currentState) {
    if (inputString.length() == 0) {
      if (stack.empty()) {
        stringAccepted = true;
      }
    }
    if (stack.empty()) {
      return;
    }
    Vector<Map.Entry<State, Vector<Symbol>>> possibleTransitions = findTransitions(inputString, currentState);
    if (possibleTransitions.size() == 0) {
      return;
    }
    Stack<Symbol> stackSnapShot = new Stack<Symbol>();
    stackSnapShot.addAll(stack);
    for (int i = 0; i < possibleTransitions.size(); i++) {
      stack.removeAllElements();
      stack.addAll(stackSnapShot);
      printTransition(inputString, possibleTransitions.get(i), currentState, stackSnapShot);
      executeTransition(inputString, possibleTransitions.get(i));
      if (stringAccepted == true) break;
    }
  }

  //  Busca las transiciones posibles dado un estado, la cadena de entrada y el top de la pila
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

  //  Ejecuta una transición dada, cambiando la configuración del autómata
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

  //  Imprime por consola la transición que se está llevando a cabo junto con la información del autómata
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

  //  Imprime un título para la tabla
  private void printTitle() {
    System.out.printf(PRINT_PROPORTIONS + "\n", "estado", "simbolo consumido", "cadena", "top de la pila", "transición");
  }

  // Comprueba si la definición del autómata es válida
  private Boolean validateAutomaton() {
    if (!states.contains(initialState)) return false;
    if (!stackAlphabet.getSymbols().contains(initialStackSymbol)) return false;
    for (Map.Entry<Map.Entry<State, Vector<Symbol>>, Set<Map.Entry<State, Vector<Symbol>>>> entry : 
                                                        transitions.getTransitionsMap().entrySet()) {
      Map.Entry<State, Vector<Symbol>> key = entry.getKey();
      Set<Map.Entry<State, Vector<Symbol>>> value = entry.getValue();
      if (!states.contains(key.getKey())) return false;
      if (!key.getValue().get(0).value.equals(".") && !alphabet.getSymbols().contains(key.getValue().get(0))) return false;
      if (!stackAlphabet.getSymbols().contains(key.getValue().get(1))) return false;
      
      Iterator<Map.Entry<State, Vector<Symbol>>> iter = value.iterator();
      while (iter.hasNext()) {
        Map.Entry<State, Vector<Symbol>> tmpEntry = iter.next();
        if (!states.contains(tmpEntry.getKey())) return false;
        for (int i = 0; i < tmpEntry.getValue().size(); i++) {
          if (!tmpEntry.getValue().get(i).value.equals(".")
              && !tmpEntry.getValue().get(i).value.equals("&")
              && !stackAlphabet.getSymbols().contains(tmpEntry.getValue().get(i))) return false;
        }
      }                                                 
    }
    return true; 
  }
}