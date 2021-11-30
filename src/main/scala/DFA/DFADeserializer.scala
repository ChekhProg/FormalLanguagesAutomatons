package DFA

import State.State

import org.json4s._
import org.json4s.native.JsonMethods._

import scala.io.{BufferedSource, Source}

case class DFAData(states: Set[String],
                   alphabet: Set[String],
                   transitions: Map[String, Map[String, String]],
                   initialState: String,
                   finalStates: Set[String])

object DFADeserializer {
  def deserialize(path: String): DFA = {
    val bufferedSource: BufferedSource = Source.fromFile(path)
    val jsonString: String = bufferedSource.getLines.mkString
    bufferedSource.close

    val jsonObject = parse(jsonString)
    implicit val formats: Formats = DefaultFormats

    val tempFSM = jsonObject.extract[DFAData]

    val states: Set[State] = tempFSM.states.map(x => State(x))
    val alphabet: Set[Char] = tempFSM.alphabet.map(x => x.charAt(0))
    val transitions: Map[State, Map[Char, State]] = tempFSM.transitions.map(
      x =>
        (State(x._1),
          x._2.map(y => (y._1.charAt(0), State(y._2)))))
    val initialState: State = State(tempFSM.initialState)
    val finalStates: Set[State] = tempFSM.finalStates.map(x => State(x))

    new DFA(states, alphabet, transitions, initialState, finalStates, initialState)
  }
}
