package pokerhands

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch

class PokerDeckTest : FunSpec({
    val deck = Deck()

    test("deck cards should be 52") {
        deck.cards.size shouldBe 52
    }

    test("deck should contain 13 cards each suit") {
        deck.cards.groupBy { it.suit }.forEach { (k, v) ->
            withClue("Suit $k should have 13 cards but has: $v") {
                v.size shouldBe 13
            }
        }
    }

    test("deck should contain 4 suits for each value") {
        deck.cards.groupBy { it.value }.forEach { (k, v) ->
            withClue("Suit $k should have 4 cards but has: $v") {
                v.size shouldBe 4
            }
        }
    }

    test("deal should be valid") {
        repeat(10) {
            val deal = Deck().deal()
            deal shouldMatch "Black: ([2-9TJQKA][SHCD] ){5} White: ([2-9TJQKA][SHCD] ){4}[2-9TJQKA][SHCD]".toRegex()
        }

        val deal = Deck().deal(listOf("Tom", "Jerry", "Spike"))
        deal shouldMatch "Tom: ([2-9TJQKA][SHCD] ){5} Jerry: ([2-9TJQKA][SHCD] ){5} Spike: ([2-9TJQKA][SHCD] ){4}[2-9TJQKA][SHCD]".toRegex()
    }
})

