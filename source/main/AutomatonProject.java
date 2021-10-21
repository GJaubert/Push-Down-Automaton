package source.main;

import java.util.Scanner;

public class AutomatonProject {
  public static void main(String args[]) {
    try {
      if (args == null) {
        System.out.println("No ha especificado un fichero de entrada");
      } 
      PdaReader reader = new PdaReader(args[0]);
      Automaton automaton = new Automaton(reader.getStates(), reader.getAlphabet(), 
                                          reader.getStackAlphabet(), reader.getinitialState(),
                                          reader.getStartingStackSymbol(), reader.getTransitions());
      Scanner userInput = new Scanner(System.in); 
      System.out.println("Introduzca una cadena a comprobar: ");                                
      while (true) {
        String input = userInput.nextLine();
        if (input.equals("salir")) break;
        automaton.checkString(input);
      }
      userInput.close();
    } catch (Exception error) {
      System.out.println(error);
    }

  }
}
