package pokerhands

import java.security.InvalidParameterException

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

class Deck {
    val cards: MutableList<Card> = mutableListOf()
    private val values = ('2'..'9') + setOf('T', 'J', 'Q', 'K', 'A')
    private val suits = setOf('C', 'D', 'H', 'S')
    init {
        cards.addAll(suits.map { suit ->
            values.map { value -> Card(value, suit) }
        }.flatten().shuffled())
    }

    fun deal(playerNames: List<String> = listOf("Black", "White")): String {
        val mapPlayerCards = playerNames.map { it to mutableListOf<Card>() }
        repeat(5) {
            mapPlayerCards.forEach { (_, playerCards) ->
                playerCards.add(cards.removeAt(0))
            }
        }
        return mapPlayerCards.joinToString("  ") { "${it.first.trim()}: ${it.second.joinToString(" ").trim()}"}
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
