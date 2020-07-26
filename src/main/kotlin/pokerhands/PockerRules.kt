package pokerhands

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
