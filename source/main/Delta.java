package source.main;

import java.util.*;
import source.main.Symbol;
import source.main.State;

public class Delta {
  private Map<Map.Entry<State, Vector<Symbol>>, Set<Map.Entry<State, Vector<Symbol>>>> transitions;
}
