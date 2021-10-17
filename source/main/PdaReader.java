package source.main;

import java.io.File;
import java.util.*;

public class PdaReader {

  private File file_;
  private Scanner scan_;
  private String currentLine;
  private Set<Character> inputAlphabet;
  private Set<String> inputStates;

  PdaReader(String inputFile) {
    file_ = new File(inputFile);
    inputStates = new HashSet<String>();

    try {
      if (file_.exists()) {
        scan_ = new Scanner(file_);
      }
      else {
        throw new Exception("File not found");
      }
      removeComments();
      readStates();
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
      String[] states = currentLine.split(" ");
      for (int i = 0; i < states.length; i++) {
        //inputStates.add(states[i]);
        System.out.println(states[i]);
      }
    } else {
      System.out.println("No States found");
    }
  }
}
