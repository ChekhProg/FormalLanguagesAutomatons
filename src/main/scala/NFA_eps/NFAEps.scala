package NFA_eps

import State.State

import scala.annotation.tailrec
import scala.collection.IterableOnce.iterableOnceExtensionMethods
import scala.collection.mutable
import scala.util.{Failure, Success, Try}

class NFAEps(states: Set[State],
             alphabet: Set[Char],
             transitions: Map[State, Map[Char, Set[State]]],
             initialState: State,
             finalStates: Set[State],
             var currentStates: Set[State]) {
  def checkInitialStates(inputString: String): Unit = {
    if (!states.contains(initialState))
      throw new Exception("Initial state is not in list of correct states")
    if (!finalStates.subsetOf(states))
      throw new Exception("Final states is not in list of correct states")
    if (!inputString.toSet.subsetOf(alphabet))
      throw new Exception("Not all symbols of input in alphabet")
  }

  @tailrec
  final def findClosing(closing: Set[State]): Set[State] = {
    val tmpClosing : mutable.Set[State] = mutable.Set[State]().empty.addAll(closing)
    for (state <- closing) {
      val states = Try(transitions(state)('e'))
      states match {
        case Success(value) =>
          tmpClosing ++= value
        case Failure(_) => ()
      }
    }
    if (closing == tmpClosing.toSet)
      closing
    else
      findClosing(tmpClosing.toSet)
  }

  def execute(input: String): Unit = {

    checkInitialStates(input)  // Проверка начальных условий

    currentStates = findClosing(currentStates)  // Добавление eps-переходов к начальному состоянию

    for (char <- input) {  // Проведение каждого символа через автомат
      val newStates = mutable.Set[State]().empty
      for (currentState <- currentStates) {
        val states = Try(transitions(currentState)(char))
        states match {
          case Success(value) =>
            newStates ++= value
          case Failure(_) => ()
        }
      }
      currentStates = newStates.toSet  //  Переход по символу
      currentStates ++= findClosing(newStates.toSet)  // Добавление eps-переходов
    }


    if ((finalStates & currentStates).nonEmpty)
      println("Строка принята")
    else
      println("Строка отвергнута")
  }
}

