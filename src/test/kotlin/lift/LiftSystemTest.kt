package lift

import io.kotest.core.spec.style.FunSpec

class LiftSystemTest : FunSpec({
    test("do somethig") {
        val liftA = Lift("A", 0)
        val lifts = LiftSystem(listOf(0, 1), listOf(liftA), emptyList())
        lifts.tick()
        println(LiftSystemPrinter().print(lifts))
    }
})
