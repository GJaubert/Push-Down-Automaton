package source.main;

import java.io.File;
import java.util.*;

public class PdaReader {

  private File file_;
  private Scanner scan_;
  private String currentLine;
  private Set<String> inputAlphabet;
  private Set<String> inputStackAlphabet;
  private Set<String> inputStates;
  private String initialState;
  private String startingStackSymbol;
  private String finalState;
  private Vector<Vector<String>> inputTransitions;

  PdaReader(String inputFile) {
    file_ = new File(inputFile);
    inputStates = new HashSet<String>();
    inputAlphabet = new HashSet<String>();
    inputStackAlphabet = new HashSet<String>();
    inputTransitions = new Vector<Vector<String>>();

    try {
      if (file_.exists()) {
        scan_ = new Scanner(file_);
      }
      else {
        throw new Exception("File not found");
      }
      removeComments();
      readStates();
      readAlphabet();
      readStackAlphabet();
      readinitialState();
      readStartingStackSymbol();
      readTransitions();
    } catch(Exception error) {
      System.out.println(error);
    }
  }

  private void removeComments() {
    while (scan_.hasNextLine()) {
      currentLine = scan_.nextLine(); 
      if ((currentLine.charAt(0) == '#')) {
        //System.out.println(currentLine);
        continue;
      }
      else
        break;
    }
  }

  private void readStates() {
    if (currentLine.length() != 0) {
      //System.out.println(currentLine);
      String[] states = currentLine.split(" ");
      for (int i = 0; i < states.length; i++) {
        inputStates.add(states[i]);
        //System.out.println(states[i]);
      }
    } else {
      System.out.println("No States found");
    }
  }

  private void readAlphabet() {
    currentLine = scan_.nextLine();
    if (currentLine.length() != 0) {
      String[] alphabetSymbols = currentLine.split(" ");
      for (int i = 0; i < alphabetSymbols.length; i++) {
        inputAlphabet.add(alphabetSymbols[i]);
        //System.out.println(alphabetSymbols[i]);
      }
    } else {
      System.out.println("No Alphabet found");
    }
  }

  private void readStackAlphabet() {
    currentLine = scan_.nextLine();
    if (currentLine.length() != 0) {
      String[] stackAlphabetSymbols = currentLine.split(" ");
      for (int i = 0; i < stackAlphabetSymbols.length; i++) {
        inputStackAlphabet.add(stackAlphabetSymbols[i]);
        //System.out.println(stackAlphabetSymbols[i]);
      }
    } else {
      System.out.println("No Stack Alphabet found");
    }
  }

  private void readinitialState() {
    currentLine = scan_.nextLine();
    if (currentLine.length() != 0) {
      initialState = currentLine;
      //System.out.println(initialState);
    } else {
      System.out.println("No Starting State found");
    }
  }

  private void readStartingStackSymbol() {
    currentLine = scan_.nextLine();
    if (currentLine.length() != 0) {
      startingStackSymbol = currentLine;
      //System.out.println(startingStackSymbol);
    } else {
      System.out.println("No Starting Stack Symbol found");
    }
  }

  private void readFinalState() {
    currentLine = scan_.nextLine();
    if (currentLine.length() != 0) {
      finalState = currentLine;
      System.out.println(finalState);
    } else {
      System.out.println("No Final State found");
    }
  }

  private void readTransitions() {
    while (scan_.hasNextLine()) {
      currentLine = scan_.nextLine();
      String[] splittedTransition = currentLine.split(" ");
      Vector<String> currentTransition = new Vector<String>();
      Collections.addAll(currentTransition, splittedTransition);
      inputTransitions.add(currentTransition);
      // for (int j = 0; j < currentTransition.size(); j++) {
      //   System.out.println(currentTransition.get(j));
      // }
    }
  }

  public Set<String> getAlphabet() {
    return inputAlphabet;
  }

  public Set<String> getStackAlphabet() {
    return inputStackAlphabet;
  }

  public Set<String> getStates() {
    return inputStates;
  }

  public String getinitialState() {
    return initialState;
  }

  public String getStartingStackSymbol() {
    return startingStackSymbol;
  }

  public Vector<Vector<String>> getTransitions() {
    return inputTransitions;
  }
}
