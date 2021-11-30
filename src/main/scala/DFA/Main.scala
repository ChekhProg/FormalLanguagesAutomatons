package DFA

object Main extends App {
  //  val DFA.FSM = DFA.FSMDeserializer.deserialize("src/main/resources/dfa_example.json")
  //  DFA.FSM.execute("010010010")
  val dfa = DFADeserializer.deserialize("src/main/resources/dfa_example2.json")
  dfa.execute("abbaabbababaaba")
}
