import java.security.InvalidParameterException

fun main(args: Array<String>) {
    println("~~~ Welcome to the casino !!! ~~~")
    var input = if (args.isEmpty()) {
        println("Enter players deal: ")
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

    init {
        hands = input.split("  ")
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

class PokerRules {
    fun settleHighestHandWinner(result1: PokerResult.HightCard, result2: PokerResult.HightCard): PokerResult.HightCard? {
        val highestCard = highestCardFromList(result1.supportCards, result2.supportCards)
        return when {
            highestCard == null -> null
            result1.supportCards.contains(highestCard) -> result1.copy(winHighestCard = highestCard)
            result2.supportCards.contains(highestCard) -> result2.copy(winHighestCard = highestCard)
            else -> null
        }
    }

    fun settleOnePairWinner(result1: PokerResult.OnePair, result2: PokerResult.OnePair): PokerResult.OnePair? {
        return when {
            result1.card.intValue > result2.card.intValue -> result1
            result1.card.intValue < result2.card.intValue -> result2
            else -> {
                val highestCard = highestCardFromList(result1.supportCards, result2.supportCards)
                when {
                    highestCard == null -> null
                    result1.supportCards.contains(highestCard) -> result1.copy(winHighestCard = highestCard)
                    result2.supportCards.contains(highestCard) -> result2.copy(winHighestCard = highestCard)
                    else -> null
                }
            }
        }
    }

    fun settleTwoPairsWinner(result1: PokerResult.TwoPairs, result2: PokerResult.TwoPairs): PokerResult.TwoPairs? {
        return when {
            result1.cards.first.intValue > result2.cards.first.intValue -> result1
            result1.cards.first.intValue < result2.cards.first.intValue -> result2
            result1.cards.second.intValue > result2.cards.second.intValue -> result1
            result1.cards.second.intValue < result2.cards.second.intValue -> result2
            else -> {
                val highestCard = highestCardFromList(result1.supportCards, result2.supportCards)
                when {
                    highestCard == null -> null
                    result1.supportCards.contains(highestCard) -> result1.copy(winHighestCard = highestCard)
                    result2.supportCards.contains(highestCard) -> result2.copy(winHighestCard = highestCard)
                    else -> null
                }
            }
        }
    }

    fun settleThreeOfAKindWinner(result1: PokerResult.ThreeKind, result2: PokerResult.ThreeKind): PokerResult.ThreeKind? {
        return when {
            result1.card.intValue > result2.card.intValue -> result1
            result1.card.intValue < result2.card.intValue -> result2
            else -> null
        }
    }

    fun settleStraightWinner(result1: PokerResult.Straight, result2: PokerResult.Straight): PokerResult.Straight? {
        return when {
            result1.card.intValue > result2.card.intValue -> result1
            result1.card.intValue < result2.card.intValue -> result2
            else -> null
        }
    }

    fun settleFlushWinner(result1: PokerResult.Flush, result2: PokerResult.Flush): PokerResult.Flush? {
        val highestCard = highestCardFromList(result1.supportCards, result2.supportCards)
        return when {
            highestCard == null -> null
            result1.supportCards.contains(highestCard) -> result1.copy(winHighestCard = highestCard)
            result2.supportCards.contains(highestCard) -> result2.copy(winHighestCard = highestCard)
            else -> null
        }
    }

    fun settleFullWinner(result1: PokerResult.Full, result2: PokerResult.Full): PokerResult.Full? {
        return when {
            result1.cards.first.intValue > result2.cards.second.intValue -> result1
            result1.cards.first.intValue < result2.cards.second.intValue -> result2
            else -> null
        }
    }

    fun settleFourOfAKindWinner(result1: PokerResult.FourKind, result2: PokerResult.FourKind): PokerResult.FourKind? {
        return when {
            result1.card.intValue > result2.card.intValue -> result1
            result1.card.intValue < result2.card.intValue -> result2
            else -> null
        }
    }

    fun settleStraightFlushWinner(result1: PokerResult.StraightFlush, result2: PokerResult.StraightFlush): PokerResult.StraightFlush? {
        return when {
            result1.card.intValue > result2.card.intValue -> result1
            result1.card.intValue < result2.card.intValue -> result2
            else -> null
        }
    }

    private fun highestCardFromList(list1: List<Card>, list2: List<Card>): Card? {
        val list1Values = list1.map { it.intValue }
        val list2Values = list2.map { it.intValue }
        val onlyInList1 = list1.filterNot { list2Values.contains(it.intValue) }.maxBy { it.intValue }
        val onlyInList2 = list2.filterNot { list1Values.contains(it.intValue) }.maxBy { it.intValue }
        return listOfNotNull(onlyInList1, onlyInList2).maxBy { it.intValue }
    }

    private fun groupCards(cards: List<Card>) =
            cards.groupBy { card -> cards.count { it.intValue == card.intValue } }

    fun handResult(hand: Hand): PokerResult {
        val groupedCards = groupCards(hand.cards)
        return when {
            isStraightFlush(hand) -> PokerResult.StraightFlush(hand.name, hand.highestCard())
            isFlush(hand) -> PokerResult.Flush(hand.name, hand.cards)
            isStraight(hand) -> PokerResult.Straight(hand.name, hand.highestCard())
            isFourOfAKind(groupedCards) -> PokerResult.FourKind(hand.name, groupedCards.getValue(4).first())
            isFull(groupedCards) -> fullResult(hand.name, groupedCards)
            isThreeOfAKind(groupedCards) -> PokerResult.ThreeKind(hand.name, groupedCards.getValue(3).first())
            isTwoPairs(groupedCards) -> twoPairsResult(hand.name, groupedCards)
            isAPair(groupedCards) -> onePairResult(hand.name, groupedCards)
            else -> PokerResult.HightCard(hand.name, hand.cards)
        }
    }

    private fun isAPair(groupedCards: Map<Int, List<Card>>) =
            groupedCards[2]?.size == 2

    private fun isTwoPairs(groupedCards: Map<Int, List<Card>>) =
            groupedCards[2]?.size == 4

    private fun isFull(groupedCards: Map<Int, List<Card>>) =
            isThreeOfAKind(groupedCards) && groupedCards[2] != null

    private fun isThreeOfAKind(groupedCards: Map<Int, List<Card>>) =
            groupedCards[3] != null

    private fun isFourOfAKind(groupedCards: Map<Int, List<Card>>) =
            groupedCards[4] != null

    private fun isStraight(hand: Hand) = hand.isNotSameSuit() && hand.isStraight()

    private fun isFlush(hand: Hand) = hand.isSameSuit() && hand.isNotStraight()

    private fun isStraightFlush(hand: Hand) = hand.isSameSuit() && hand.isStraight()

    private fun onePairResult(name: String, groupedCards: Map<Int, List<Card>>): PokerResult.OnePair {
        val pairCard = groupedCards[2]?.first()!!
        val restOfTheCards = groupedCards
                .filterKeys { it != 2 }
                .values
                .flatten()
        return PokerResult.OnePair(name, pairCard, restOfTheCards)
    }

    private fun twoPairsResult(name: String, groupedCards: Map<Int, List<Card>>): PokerResult.TwoPairs {
        val pairCardHigh = groupedCards.getValue(2).maxBy { it.intValue } ?: throw Exception("There should be a max")
        val pairCardLow = groupedCards.getValue(2).first { it.intValue != pairCardHigh.intValue }
        val restOfTheCards = groupedCards
                .filterKeys { it != 2 }
                .values
                .flatten()
        return PokerResult.TwoPairs(name, pairCardHigh to pairCardLow, restOfTheCards)
    }

    private fun fullResult(name: String, groupedCards: Map<Int, List<Card>>): PokerResult.Full {
        val threeCard = groupedCards.getValue(3).first()
        val twoCard = groupedCards.getValue(2).first()

        return PokerResult.Full(name, threeCard to twoCard)
    }
}

data class Card(val value: Char, val suit: Char) {
    val intValue: Int = cardValue(value)
    val suitName: String = suitName(suit)
    val valueName: String = cardName(value)

    constructor(card: String) : this(card.first(), card.last())

    private fun cardValue(value: Char): Int =
            when (value) {
                in '2'..'9' -> value - '0'
                'T' -> 10
                'J' -> 11
                'Q' -> 12
                'K' -> 13
                'A' -> 14
                else -> throw InvalidParameterException("$value is not a valid card")
            }

    private fun suitName(suit: Char): String =
            when (suit) {
                'C' -> "♣"
                'D' -> "♦"
                'H' -> "♥"
                'S' -> "♤"
                else -> throw InvalidParameterException("$suit is not a valid card suit")
            }

    private fun cardName(value: Char): String =
            when (value) {
                in '2'..'9' -> "$value"
                'T' -> "10"
                'J' -> "Jack"
                'Q' -> "Queen"
                'K' -> "King"
                'A' -> "Ace"
                else -> throw InvalidParameterException("$value is not a valid card")
            }

    override fun toString(): String {
        return "$value$suit"
    }
}

class Hand(val name: String, val cards: List<Card>) {
    companion object {
        fun fromString(hand: String): Hand {
            return hand.split(": ").let {
                if (it.size != 2) throw InvalidParameterException("Hand: $hand is not valid")
                Hand(it.first(), loadHand(it.last()))
            }
        }

        private fun loadHand(hand: String): List<Card> {
            return hand.split(" ").map { Card(it) }
        }
    }

    fun isSameSuit(): Boolean = cards.all { it.suit == cards.first().suit }
    fun isStraight(): Boolean {
        val values = cards.map { it.intValue }.sorted()
        val smallest = values.first()
        return values.containsAll((smallest + 1..smallest + 4).toList())
    }

    fun isNotStraight() = !isStraight()
    fun isNotSameSuit() = !isSameSuit()

    fun highestCard(): Card = cards.maxBy { it.intValue } ?: cards.first()

    override fun toString() = "$name: ${cards.joinToString(" ")}"
}

sealed class PokerResult(open val player: String, val rank: Int, val label: String) {
    data class HightCard(override val player: String, val supportCards: List<Card>, val winHighestCard: Card? = null)
        : PokerResult(player, 1, "high card")

    data class OnePair(override val player: String, val card: Card, val supportCards: List<Card>, val winHighestCard: Card? = null)
        : PokerResult(player, 2, "pair")

    data class TwoPairs(override val player: String, val cards: Pair<Card, Card>, val supportCards: List<Card>, val winHighestCard: Card? = null)
        : PokerResult(player, 3, "two pairs")

    data class ThreeKind(override val player: String, val card: Card)
        : PokerResult(player, 4, "three of a kind")

    data class Straight(override val player: String, val card: Card)
        : PokerResult(player, 5, "straight")

    data class Flush(override val player: String, val supportCards: List<Card>, val winHighestCard: Card? = null)
        : PokerResult(player, 6, "flush")

    data class Full(override val player: String, val cards: Pair<Card, Card>)
        : PokerResult(player, 7, "full house")

    data class FourKind(override val player: String, val card: Card)
        : PokerResult(player, 8, "four of a kind")

    data class StraightFlush(override val player: String, val card: Card)
        : PokerResult(player, 9, "straight flush")
}

fun PokerResult.winDetail(): String =
        when (this) {
            is PokerResult.StraightFlush -> card.valueName
            is PokerResult.FourKind -> card.valueName
            is PokerResult.Full -> "${cards.first.valueName} over ${cards.second.valueName}"
            is PokerResult.Flush -> supportCards.first().suitName + (this.winHighestCard?.let { " and high card: ${it.valueName}" }
                    ?: "")
            is PokerResult.Straight -> card.valueName
            is PokerResult.ThreeKind -> card.valueName
            is PokerResult.TwoPairs -> "${cards.first.valueName} and ${cards.second.valueName}" + (this.winHighestCard?.let { " and high card: ${it.valueName}" }
                    ?: "")
            is PokerResult.OnePair -> card.valueName + (this.winHighestCard?.let { " and high card: ${it.valueName}" }
                    ?: "")
            is PokerResult.HightCard -> {
                val winCard = winHighestCard
                        ?: (this.supportCards.maxBy { it.intValue } ?: throw Exception("Should never be null"))

                winCard.valueName
            }
        }
