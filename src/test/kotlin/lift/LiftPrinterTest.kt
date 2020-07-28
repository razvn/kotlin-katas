package lift

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class LiftPrinterTest : FunSpec ({
    test("no lifts") {
        val floors = listOf(0, 1, 2, 3)
        val lifts = emptyList<Lift>()
        val calls = emptyList<Call>()
        val liftSystem = LiftSystem(floors, lifts, calls)
        LiftSystemPrinter().print(liftSystem) shouldBe """
            3    3
            2    2
            1    1
            0    0
        
        """.trimIndent()
    }

    test("one lift no doors") {
        val liftA = Lift("A", 0, listOf(2,3))
        val liftSystem = LiftSystem(listOf(0,1,2,3), listOf(liftA), emptyList())
        LiftSystemPrinter().printWithoutDoors(liftSystem) shouldBe """
            3    * 3
            2    * 2
            1      1
            0    A 0
        
        """.trimIndent()
    }

    test("sample lift system") {
        val liftA = Lift("A", 3, listOf(0), false)
        val liftB = Lift("B", 2)
        val liftC = Lift("C", 2, doorsOpen = true)
        val liftD = Lift("D", 0, listOf(0), doorsOpen = false)
        val liftSystem = LiftSystem(listOf(0, 1, 2, 3), listOf(liftA, liftB, liftC, liftD), listOf(Call(1, Direction.DOWN)))
        // println(LiftSystemPrinter().print(liftSystem))
        LiftSystemPrinter().print(liftSystem) shouldBe """
            3     [A]               3
            2          [B]  ]C[     2
            1 v                     1
            0      *            [*D]0
        
        """.trimIndent()
    }

    test("large lift system") {
        val liftA = Lift("A", 3, listOf(3,5,7), false)
        val liftB = Lift("B", 2, doorsOpen = true)
        val liftC = Lift("C", -2, listOf(-2, 0), doorsOpen = false)
        val liftD = Lift("D", 8, listOf(0, -1, -2), doorsOpen = true)
        val liftSVC = Lift("SVC", 10, listOf(0, -1), doorsOpen = false)
        val liftF = Lift("F", 8, doorsOpen = false)
        val liftSystem = LiftSystem(
                listOf(-2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                listOf(liftA, liftB, liftC, liftD, liftSVC, liftF),
                listOf(Call(1, Direction.DOWN), Call(6, Direction.DOWN), Call(5, Direction.UP), Call(5, Direction.DOWN), Call(-1, Direction.UP)))
        // println(LiftSystemPrinter().print(liftSystem))
        LiftSystemPrinter().print(liftSystem) shouldBe """
            10                         [SVC]     10
             9                                    9
             8                    ]D[         [F] 8
             7      *                             7
             6 v                                  6
             5 ^v   *                             5
             4                                    4
             3    [*A]                            3
             2          ]B[                       2
             1 v                                  1
             0                *    *    *         0
            -1 ^                   *    *        -1
            -2              [*C]   *             -2
        
        """.trimIndent()
    }


})
