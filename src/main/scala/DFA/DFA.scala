package DFA

import State.State

import scala.util.{Failure, Success, Try}

class DFA(states: Set[State],
          alphabet: Set[Char],
          transitions: Map[State, Map[Char, State]],
          initialState: State,
          finalStates: Set[State],
          var currentState: State) {
  def checkInitialStates(inputString: String): Unit = {
    if (!states.contains(initialState))
      throw new Exception("Initial state is not in list of correct states")
    if (!finalStates.subsetOf(states))
      throw new Exception("Final states is not in list of correct states")
    if (!inputString.toSet.subsetOf(alphabet))
      throw new Exception("Not all symbols of input in alphabet")
  }

  def execute(input: String): Unit = {
    checkInitialStates(input)

    for (char <- input) {
      val state = Try(transitions(currentState)(char))
      state match {
        case Success(value) =>
          currentState = value
        case Failure(exception) =>
          throw new Exception("Transition not correct")
      }
    }
    if (finalStates.contains(currentState))
      println("String accepted. Terminal state: " + currentState)
    else
      println("String not accepted. Not terminal state: " + currentState)
  }
}
