package source.main;

import source.main.Automaton;
import source.main.PdaReader;

public class AutomatonProject {
  public static void main(String args[]) {
    PdaReader reader = new PdaReader("../resources/APv.txt");
    Automaton automaton = new Automaton(reader.getStates(), reader.getAlphabet(), 
                                        reader.getStackAlphabet(), reader.getinitialState(),
                                        reader.getStartingStackSymbol(), reader.getTransitions());
  }
}
