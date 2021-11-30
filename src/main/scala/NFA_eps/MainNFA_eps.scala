package NFA_eps

object MainNFA_eps extends App {
  def evenZeroesOnes(input: String): Unit = {
    val nfa = NFAEpsDeserializer.deserialize("src/main/resources/nfaeps_example.json")
    println("Строка: " + input)
    println("Проверка, содержит ли строка четное количество нулей или единиц:")
    nfa.execute(input)
    println()
  }
  def asmNumber(input: String): Unit = {
    val nfa = NFAEpsDeserializer.deserialize("src/main/resources/nfaeps_example2.json")
    println("Строка: " + input)
    println("Проверка строки на соотвествие ASM-числу (кроме шестнадцатиричных):")
    nfa.execute(input)
    println()
  }
  evenZeroesOnes("01011")
  asmNumber("034334")
  asmNumber("343d")
}
