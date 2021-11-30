package NFA

import State.State

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

class NFA(states: Set[State],
          alphabet: Set[Char],
          transitions: Map[State, Map[Char, Set[State]]],
          initialState: State,
          finalStates: Set[State],
          var currentStates: Set[State])
{
  def checkInitialStates(inputString: String): Unit = {
    if (!states.contains(initialState))
      throw new Exception("Initial state is not in list of correct states")
    if (!finalStates.subsetOf(states))
      throw new Exception("Final states is not in list of correct states")
    if (!inputString.toSet.subsetOf(alphabet))
      throw new Exception("Not all symbols of input in alphabet")
  }

  def execute(input: String): Unit = {

    checkInitialStates(input)  // Проверка начального состояния и символов входной строки на соотвествие

    for (char <- input) {  // Перебираем каждый символ
      val newStates = mutable.Set[State]().empty

      for (currentState <- currentStates) {   // Проход по каждом текущему состоянию
        val states = Try(transitions(currentState)(char))
        states match {
          case Success(value) =>
            newStates ++= value
          case Failure(_) => ()
        }
      }
      currentStates = newStates.toSet
    }

    if ((finalStates & currentStates).nonEmpty)
      println("Строка принята")
    else
      println("Строка отвергнута")
  }
}

