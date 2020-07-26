package diamond

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DiamondTest  : FunSpec({
    val diamond = Diamond()

    test("A") {
        diamond.print('A') shouldBe "A"
    }
    /*
    // steps

    // handling sequence
    test("B should give character sequence"){
        diamond.print('B') shouldBe "AB"
    }

    // handling repetition
    test("B should give character sequence"){
        diamond.print('B') shouldBe "ABB"
    }

    // handling separate lines
    test("B should give character sequence"){
        diamond.print('B') shouldBe "A\nBB\n"
    }

    // handling indentation
    test("B should give character sequence"){
        diamond.print('B') shouldBe " A\nBB\n"
    }

    // and finally symmetry
     */

    test("B should have separate lines"){
        diamond.print('B') shouldBe " A\nB B\n A"
    }

    test("B") {
        diamond.print('B') shouldBe """
        | A
        |B B
        | A
        """.trimMargin()
    }

    test("C") {
        diamond.print('C') shouldBe """
        |  A
        | B B
        |C   C
        | B B
        |  A
        """.trimMargin()
    }

    test("D") {
        diamond.print('D') shouldBe """
        |   A
        |  B B
        | C   C
        |D     D
        | C   C
        |  B B
        |   A
        """.trimMargin()
    }

    test("All") {
        ('A'..'Z').map { diamond.print(it) }
    }
})
