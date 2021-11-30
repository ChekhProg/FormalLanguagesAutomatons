package NFA

object MainNFA extends App {
  def endWithZeroes(input: String): Unit = {
    val nfa = NFADeserializer.deserialize("src/main/resources/nfa_example.json")
    println("Строка: " + input)
    println("Проверка, оканчивается ли строка на 00:")
    nfa.execute(input)
    println()
  }
  endWithZeroes("01011110011")
  endWithZeroes("0101111001100")
}
