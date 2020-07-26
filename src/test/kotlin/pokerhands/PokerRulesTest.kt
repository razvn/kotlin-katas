package pokerhands

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.beOfType
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class PokerRulesTest : FunSpec({
    val rules = PokerRules()

    context("Hand result tests") {

        test("return high card") {
            val hand1 = Hand.fromString("Black: 2H 3D AD TS KD")
            val result = rules.handResult(hand1)

            result should beOfType<PokerResult.HightCard>()
            result.rank shouldBe 1
            result.label shouldBe "high card"
            (result as PokerResult.HightCard).supportCards shouldContainExactlyInAnyOrder hand1.cards

        }

        test("return one pair") {
            val hand1 = Hand.fromString("Black: 2H 3D AD AS KD")
            val result = rules.handResult(hand1)

            result should beOfType<PokerResult.OnePair>()
            result.rank shouldBe 2
            result.label shouldBe "pair"

            val pair = (result as PokerResult.OnePair)
            pair.card.value shouldBe 'A'
            pair.supportCards shouldContainExactlyInAnyOrder hand1.cards.filterNot { it.value == 'A'}
        }

        test("return two pairs") {
            val hand1 = Hand.fromString("Black: 2H 2D AD AS KD")
            val result = rules.handResult(hand1)

            result should beOfType<PokerResult.TwoPairs>()
            result.rank shouldBe 3
            result.label shouldBe "two pairs"

            println(result)
            val twoPairs = (result as PokerResult.TwoPairs)
            twoPairs.cards.first.value shouldBe 'A'
            twoPairs.cards.second.value shouldBe '2'
            twoPairs.supportCards shouldContainExactlyInAnyOrder hand1.cards
                    .filter { it.value != 'A'&& it.value != '2'}
        }

        test("return three of a kind") {
            val hand1 = Hand.fromString("Black: AH 3D AD AS KD")
            val result = rules.handResult(hand1)

            result should beOfType<PokerResult.ThreeKind>()
            result.rank shouldBe 4
            result.label shouldBe "three of a kind"

            val threeKind = (result as PokerResult.ThreeKind)
            threeKind.card.value shouldBe 'A'
        }

        test("return full house") {
            val hand1 = Hand.fromString("Black: AH 3D AD AS 3C")
            val result = rules.handResult(hand1)

            println(result)
            result should beOfType<PokerResult.Full>()
            result.rank shouldBe 7
            result.label shouldBe "full house"

            val full = (result as PokerResult.Full)
            full.cards.first.value shouldBe 'A'
            full.cards.second.value shouldBe '3'
        }

        test("return four of a kind") {
            val hand1 = Hand.fromString("Black: AH 3D AD AS AC")
            val result = rules.handResult(hand1)

            println(result)
            result should beOfType<PokerResult.FourKind>()
            result.rank shouldBe 8
            result.label shouldBe "four of a kind"

            val fourKind = (result as PokerResult.FourKind)
            fourKind.card.value shouldBe 'A'
        }

        test("return straight flush") {
            val hand1 = Hand.fromString("Black: TD JD AD QD KD")
            val result = rules.handResult(hand1)

            println(result)
            result should beOfType<PokerResult.StraightFlush>()
            result.rank shouldBe 9
            result.label shouldBe "straight flush"

            val straight = (result as PokerResult.StraightFlush)
            straight.card.value shouldBe 'A'
        }

        test("return flush") {
            val hand1 = Hand.fromString("Black: TD JD AD QD 2D")
            val result = rules.handResult(hand1)

            println(result)
            result should beOfType<PokerResult.Flush>()
            result.rank shouldBe 6
            result.label shouldBe "flush"

            val straight = (result as PokerResult.Flush)
            straight.supportCards shouldContainExactlyInAnyOrder hand1.cards
        }

        test("return straight") {
            val hand1 = Hand.fromString("Black: TD JD AD QD KC")
            val result = rules.handResult(hand1)

            println(result)
            result should beOfType<PokerResult.Straight>()
            result.rank shouldBe 5
            result.label shouldBe "straight"

            val straight = (result as PokerResult.Straight)
            straight.card.value shouldBe 'A'
        }
    }

})
