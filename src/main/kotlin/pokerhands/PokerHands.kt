package pokerhands

import java.security.InvalidParameterException

fun main(args: Array<String>) {
    println("~~~ Welcome to the casino !!! ~~~")
    var input = if (args.isEmpty()) {
        println("Enter players deal (or blank for random hands): ")
        readLine()!!
    } else {
        args[0]
    }

    var exit = false
    while (!exit) {
        PokerHands(input)
        println("\n-----------------------------------")
        println(" Enter another input or a single letter to quit:")
        input = readLine()!!
        if (input.length == 1) {
            println("~~~ Bye ~~~")
            exit = true
        }
    }
}

class PokerHands(input: String) {
    private val hands: Map<String, Hand>
    private val rules = PokerRules()
    private val deck = Deck()

    init {
        val deal = if (input.isBlank()) deck.deal() else input
        hands = deal.split("  ")
                .map {
                    Hand.fromString(it).let { hand ->
                        hand.name to hand
                    }
                }.toMap()
        printGame()
        printWinner()
    }

    private fun printGame() {
        hands.forEach {
            println(it.value)
        }
    }

    private fun printWinner() {
        winner()
    }

    fun winner(): PokerResult? {
        val results = hands.map { rules.handResult(it.value) }
                .groupBy { it.rank }
                .toSortedMap()
        val highestHands = results[results.lastKey()] ?: throw Exception("There should always be one")

        // println(highestHands)
        val winner = if (highestHands.size == 1) {
            highestHands.first()
        } else {
            // both players have same rank
            val player1 = highestHands.first()
            val player2 = highestHands.last()

            when {
                player1 is PokerResult.HightCard && player2 is PokerResult.HightCard -> rules.settleHighestHandWinner(player1, player2)
                player1 is PokerResult.OnePair && player2 is PokerResult.OnePair -> rules.settleOnePairWinner(player1, player2)
                player1 is PokerResult.TwoPairs && player2 is PokerResult.TwoPairs -> rules.settleTwoPairsWinner(player1, player2)
                player1 is PokerResult.ThreeKind && player2 is PokerResult.ThreeKind -> rules.settleThreeOfAKindWinner(player1, player2)
                player1 is PokerResult.Straight && player2 is PokerResult.Straight -> rules.settleStraightWinner(player1, player2)
                player1 is PokerResult.Flush && player2 is PokerResult.Flush -> rules.settleFlushWinner(player1, player2)
                player1 is PokerResult.Full && player2 is PokerResult.Full -> rules.settleFullWinner(player1, player2)
                player1 is PokerResult.FourKind && player2 is PokerResult.FourKind -> rules.settleFourOfAKindWinner(player1, player2)
                player1 is PokerResult.StraightFlush && player2 is PokerResult.StraightFlush -> rules.settleStraightFlushWinner(player1, player2)

                else -> throw Exception("Results should be both same type")
            }
        }

        if (winner != null) {
            println("${winner.player} wins. - with ${winner.label}: ${winner.winDetail()}")
        } else {
            println("Tie.")
        }
        return winner
    }

    fun handFor(name: String) = hands[name] ?: throw InvalidParameterException("Unknown hand: $name")
}
