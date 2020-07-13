import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class FizzBuzzTest : FunSpec({

    context("Stage 1 - only multiples") {
        test("1 should return 1") {
            fizzBuzzStage1(1) shouldBe "1"
        }

        test("2 should return 2") {
            fizzBuzzStage1(2) shouldBe "2"
        }

        test("3 should return Fizz") {
            fizzBuzzStage1(3) shouldBe "Fizz"
        }
        test("4 should return 4") {
            fizzBuzzStage1(4) shouldBe "4"
        }
        test("5 should return Buzz") {
            fizzBuzzStage1(5) shouldBe "Buzz"
        }

        test("6 should return Fizz") {
            fizzBuzzStage1(6) shouldBe "Fizz"
        }
        test("10 should return Buzz") {
            fizzBuzzStage1(10) shouldBe "Buzz"
        }
        test("15 should return FizzBuzz") {
            fizzBuzzStage1(15) shouldBe "FizzBuzz"
        }
    }

    context("Stage 2 - multiples or contains") {
        test("1 should return 1") {
            fizzBuzzStage2(1) shouldBe "1"
        }

        test("2 should return 2") {
            fizzBuzzStage2(2) shouldBe "2"
        }

        test("3 should return Fizz") {
            fizzBuzzStage2(3) shouldBe "Fizz"
        }
        test("4 should return 4") {
            fizzBuzzStage2(4) shouldBe "4"
        }
        test("5 should return Buzz") {
            fizzBuzzStage2(5) shouldBe "Buzz"
        }

        test("6 should return Fizz") {
            fizzBuzzStage2(6) shouldBe "Fizz"
        }
        test("10 should return Buzz") {
            fizzBuzzStage2(10) shouldBe "Buzz"
        }
        test("15 should return FizzBuzz") {
            fizzBuzzStage2(15) shouldBe "FizzBuzz"
        }

        test("32 should return Fizz") {
            fizzBuzzStage2(32) shouldBe "Fizz"
        }

        test("52 should return Buzz") {
            fizzBuzzStage2(52) shouldBe "Buzz"
        }

        test("51 should return FizzBuzz") {
            fizzBuzzStage2(51) shouldBe "FizzBuzz"
        }

        test("53 should return FizzBuzz") {
            fizzBuzzStage2(51) shouldBe "FizzBuzz"
        }
    }

})
