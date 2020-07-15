import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import java.security.InvalidParameterException

class CardTest : FunSpec({
    test("card parse is valid") {
        val card = Card("2H")
        card.value shouldBe '2'
        card.intValue shouldBe 2
        card.suit shouldBe 'H'
    }
})

class HandTest : FunSpec({
    test("hand parse is valid") {
        val hand = Hand.fromString("Black: 2H 3D 5S 9C KD")

        hand.name shouldBe "Black"
        hand.cards shouldContainExactly listOf(
                Card("2H"),
                Card("3D"),
                Card("5S"),
                Card("9C"),
                Card("KD")
        )
    }

    test("isSameSuit should be false") {
        val hand = Hand.fromString("Black: 2H 3D 5S 9C KD")
        hand.isSameSuit() shouldBe false
    }

    test("isSameSuit should be true") {
        val hand = Hand.fromString("Black: 2H 3H 5H 9H KH")
        hand.isSameSuit() shouldBe true
    }

    test("isStraight should be false") {
        val hand = Hand.fromString("Black: 2H 3D 5S 9C KD")
        hand.isStraight() shouldBe false
    }

    test("isStraight should be true") {
        val hand = Hand.fromString("Black: TH JD QS KC AD")
        hand.isStraight() shouldBe true
    }

    context("highest card") {
        test("should return the max if disinct") {
            val hand = Hand.fromString("Black: 2H 3D 5S 9C KD")

            hand.highestCard().value shouldBe 'K'
        }

        test("should return the max if multiples") {
            val hand = Hand.fromString("Black: 2H 3D 3S 3C 2D")

            hand.highestCard().value shouldBe '3'
        }
    }
})

class PokerHandsTest : FunSpec({

    test("parse game input") {
        val game = PokerHands("Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH")

        game.handFor("Black").toString() shouldBe "Black: 2H 3D 5S 9C KD"
        game.handFor("White").toString() shouldBe "White: 2C 3H 4S 8C AH"

        shouldThrow<InvalidParameterException> {
            game.handFor("Unknown")
        }
    }

    context("high card winner") {
        test("winner is the player with the higest card") {
            val game = PokerHands("Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "high card"
            winner.winDetail() shouldBe "Ace"
        }

        test("winner is the player with the higest card not the same") {
            val game = PokerHands("Black: 2H 5D 4H 8S AD  White: 2C 3H 4S 8C AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "high card"
            winner.winDetail() shouldBe "5"
        }

        test("tie when same cards") {
            val game = PokerHands("Black: 2H 5D 4H 8S AD  White: 2C 5H 4S 8C AH")
            val winner = game.winner()
            winner should beNull()
        }
    }

    context("a pair winner") {
        test("a pair wins over nothing") {
            val game = PokerHands("Black: 2H 3D 5S 9C 2D  White: 2C 3H 4S 8C AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "pair"
            winner.winDetail() shouldBe "2"
        }

        test("a pair with highest value wins") {
            val game = PokerHands("Black: 2H 3D 5S 9C 2D  White: 2C 3H 4S 4C AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "pair"
            winner.winDetail() shouldBe "4"
        }

        test("player with highest card wins when both players same pair") {
            val game = PokerHands("Black: 4H 3D 5S 9C 4D  White: 2C 3H 4S 4C AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "pair"
            winner.winDetail() shouldBe "4 and high card: Ace"
        }

        test("tie when same pair and same other cards") {
            val game = PokerHands("Black: 4H 3D 2S AC 4D  White: 2C 3H 4S 4C AH")
            val winner = game.winner()
            winner should beNull()
        }

    }

    context("two pairs winner") {
        test("two pairs wins over nothing") {
            val game = PokerHands("Black: 2H 2D 5S KC KD  White: 2C 3H 4S 8C AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "two pairs"
            winner.winDetail() shouldBe "King and 2"
        }

        test("two pairs wins over one pair") {
            val game = PokerHands("Black: 2H 2D 5S KC KD  White: 2C 3H 4S AC AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "two pairs"
            winner.winDetail() shouldBe "King and 2"
        }

        test("two pairs both with highest values wins when all different") {
            val game = PokerHands("Black: 2H 2D 5S KC KD  White: 3C 3H 4S AC AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "two pairs"
            winner.winDetail() shouldBe "Ace and 3"
        }

        test("two pairs wins the one with highest values when little pair is bigger for the second one") {
            val game = PokerHands("Black: 8H 8D 5S KC KD  White: 3C 3H 4S AC AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "two pairs"
            winner.winDetail() shouldBe "Ace and 3"
        }


        test("two pairs wins the one with highest values second when highest pairs are same") {
            val game = PokerHands("Black: 8H 8D 5S AC AD  White: 3C 3H 4S AC AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "two pairs"
            winner.winDetail() shouldBe "Ace and 8"
        }

        test("two pairs wins highest card when pairs are the same") {
            val game = PokerHands("Black: 3H 3D 5S AC AD  White: 3C 3H 4S AC AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "two pairs"
            winner.winDetail() shouldBe "Ace and 3 and high card: 5"
        }

        test("two pairs tie when same pairs and same cards") {
            val game = PokerHands("Black: 3H 3D 5S AC AD  White: 3C 3H 5C AC AH")
            val winner = game.winner()
            winner should beNull()
        }
    }

    context("three of a kind winner") {
        test("three of a kind wins over nothing") {
            val game = PokerHands("Black: 3H 3D 5S 3C AD  White: 2C 3H 8S AC TH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "three of a kind"
            winner.winDetail() shouldBe "3"
        }
        test("three of a kind wins over a pair") {
            val game = PokerHands("Black: 3H 3D 5S 3C AD  White: 2C 3H 8S AC AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "three of a kind"
            winner.winDetail() shouldBe "3"
        }
        test("three of a kind wins over two pairs") {
            val game = PokerHands("Black: 3H 3D 5S 3C AD  White: 2C 8H 8S AC AH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "three of a kind"
            winner.winDetail() shouldBe "3"
        }
        test("three of a kind wins if higher than other tree of other kind") {
            val game = PokerHands("Black: 3H 3D 5S 3C AD  White: 8C 8H 8S AC TH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "three of a kind"
            winner.winDetail() shouldBe "8"
        }
    }
    context("straight winner") {
        test("straight wins over three of a kind") {
            val game = PokerHands("Black: 2H 3D 4S 5C 6D  White: 8C 8H 8S AC TH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "straight"
            winner.winDetail() shouldBe "6"
        }

        test("straight with highest value wins") {
            val game = PokerHands("Black: 2H 3D 4S 5C 6D  White: TC JH QS AC KH")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "straight"
            winner.winDetail() shouldBe "Ace"
        }

        test("tie if same highest value") {
            val game = PokerHands("Black: JH TD QS KC AD  White: TC JH QS AC KH")
            val winner = game.winner()
            winner should beNull()
        }
    }

    context("flush winner") {
        test("flush wins over straight") {
            val game = PokerHands("Black: 2H 3D 4S 5C 6D  White: 8C 6C 3C AC TC")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "flush"
            winner.winDetail() shouldBe "♣"
        }

        test("flush with highest card wins over other flush") {
            val game = PokerHands("Black: 2H TH 4H 5H AH  White: 8C 6C 3C AC TC")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "flush"
            winner.winDetail() shouldBe "♣ and high card: 8"
        }
    }

    context("full winner") {
        test("full wins over flash") {
            val game = PokerHands("Black: 2H 2D 2S 6C 6D  White: 8C 6C 3C AC TC")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "full house"
            winner.winDetail() shouldBe "2 over 6"
        }

        test("full with highest card 3 cards wins over other full") {
            val game = PokerHands("Black: 2H 2D 2S 6C 6D  White: 8C 8D 8H AS AC")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "full house"
            winner.winDetail() shouldBe "8 over Ace"
        }
    }

    context("four of a kind winner") {
        test("four of a kind wins over full") {
            val game = PokerHands("Black: 2H 2D 2S 6C 6D  White: 8C 8D 8H 8S AC")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "four of a kind"
            winner.winDetail() shouldBe "8"
        }
        test("four of a kind wins if higher than other four of other kind") {
            val game = PokerHands("Black: AH AD AS AC 6D  White: 8C 8D 8H 8S AC")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "four of a kind"
            winner.winDetail() shouldBe "Ace"
        }
    }

    context("straight flush winner") {
        test("straight flush wins over four of a kind") {
            val game = PokerHands("Black: 2D 3D 4D 5D 6D  White: 8C 8D 8H 8S AC")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "Black"
            winner.label shouldBe "straight flush"
            winner.winDetail() shouldBe "6"
        }
        test("straight flush wins if higher card than other straight flush") {
            val game = PokerHands("Black: 2D 3D 4D 5D 6D  White: 8C 9C TC JC QC")
            val winner = game.winner()
            winner shouldNot beNull()
            winner!!.player shouldBe "White"
            winner.label shouldBe "straight flush"
            winner.winDetail() shouldBe "Queen"
        }
        test("tie if two straight flushes with same higher card") {
            val game = PokerHands("Black: 8D 9D TD JD QD  White: 8C 9C TC JC QC")
            val winner = game.winner()
            winner should beNull()
        }
    }

})
