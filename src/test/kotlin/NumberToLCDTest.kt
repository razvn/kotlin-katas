import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class NumberToLCDTest : FunSpec({
    val c = NumberToLCD()
    context("part one 3 lines digits") {
        test("1 should print") {
            val lcd = c.nbToLCD(1)
            println(lcd)
            lcd shouldBe """
           >   
           >  |
           >  |
        """.trimMargin(marginPrefix = ">")
        }
        test("2 should print") {
            val lcd = c.nbToLCD(2)
            println(lcd)
            lcd shouldBe """
            > _ 
            > _|
            >|_ 
        """.trimMargin(marginPrefix = ">")
        }
        test("3 should print") {
            val lcd = c.nbToLCD(3)
            println(lcd)
            lcd shouldBe """
            > _ 
            > _|
            > _|
        """.trimMargin(marginPrefix = ">")
        }
        test("4 should print") {
            val lcd = c.nbToLCD(4)
            println(lcd)
            lcd shouldBe """
            >   
            >|_|
            >  |
        """.trimMargin(marginPrefix = ">")
        }
        test("5 should print") {
            val lcd = c.nbToLCD(5)
            println(lcd)
            lcd shouldBe """
            > _ 
            >|_ 
            > _|
        """.trimMargin(marginPrefix = ">")
        }
        test("6 should print") {
            val lcd = c.nbToLCD(6)
            println(lcd)
            lcd shouldBe """
            > _ 
            >|_ 
            >|_|
        """.trimMargin(marginPrefix = ">")
        }
        test("7 should print") {
            val lcd = c.nbToLCD(7)
            println(lcd)
            lcd shouldBe """
            > _ 
            >  |
            >  |
        """.trimMargin(marginPrefix = ">")
        }

        test("8 should print") {
            val lcd = c.nbToLCD(8)
            println(lcd)
            lcd shouldBe """
            > _ 
            >|_|
            >|_|
        """.trimMargin(marginPrefix = ">")
        }
        test("9 should print") {
            val lcd = c.nbToLCD(9)
            println(lcd)
            lcd shouldBe """
            > _ 
            >|_|
            > _|
        """.trimMargin(marginPrefix = ">")
        }

        test("19 should print") {
            val lcd = c.nbToLCD(19)
            println(lcd)
            lcd shouldBe """
            >     _ 
            >  | |_|
            >  |  _|
        """.trimMargin(marginPrefix = ">")
        }

        test("123456789 should print") {
            val lcd = c.nbToLCD(123456789)
            println(lcd)
            lcd shouldBe """
            >     _   _       _   _   _   _   _ 
            >  |  _|  _| |_| |_  |_    | |_| |_|
            >  | |_   _|   |  _| |_|   | |_|  _|
        """.trimMargin(marginPrefix = ">")
        }
    }

    context("multiple lines digits") {
        test("1 should print") {
            val lcd = c.nbToLCD(1, 1)
            println(lcd)
            lcd shouldBe """
           >   
           >  |
           >   
           >  |
           >   
        """.trimMargin(marginPrefix = ">")
        }
        test("2 should print") {
            val lcd = c.nbToLCD(2, 1)
            println(lcd)
            lcd shouldBe """
            > - 
            >  |
            > - 
            >|  
            > - 
        """.trimMargin(marginPrefix = ">")
        }
        test("3 should print") {
            val lcd = c.nbToLCD(3, 1)
            println(lcd)
            lcd shouldBe """
            > - 
            >  |
            > - 
            >  |
            > - 
        """.trimMargin(marginPrefix = ">")
        }
        test("4 should print") {
            val lcd = c.nbToLCD(4, 1)
            println(lcd)
            lcd shouldBe """
            >   
            >| |
            > - 
            >  |
            >   
        """.trimMargin(marginPrefix = ">")
        }
        test("5 should print") {
            val lcd = c.nbToLCD(5, 1)
            println(lcd)
            lcd shouldBe """
            > - 
            >|  
            > - 
            >  |
            > - 
        """.trimMargin(marginPrefix = ">")
        }
        test("6 should print") {
            val lcd = c.nbToLCD(6, 1)
            println(lcd)
            lcd shouldBe """
            > - 
            >|  
            > - 
            >| |
            > - 
        """.trimMargin(marginPrefix = ">")
        }
        test("7 should print") {
            val lcd = c.nbToLCD(7, 1)
            println(lcd)
            lcd shouldBe """
            > - 
            >  |
            >   
            >  |
            >   
        """.trimMargin(marginPrefix = ">")
        }

        test("8 should print") {
            val lcd = c.nbToLCD(8, 1)
            println(lcd)
            lcd shouldBe """
            > - 
            >| |
            > - 
            >| |
            > - 
        """.trimMargin(marginPrefix = ">")
        }
        test("9 should print") {
            val lcd = c.nbToLCD(9, 1)
            println(lcd)
            lcd shouldBe """
            > - 
            >| |
            > - 
            >  |
            > - 
        """.trimMargin(marginPrefix = ">")
        }

        test("19 should print") {
            val lcd = c.nbToLCD(19, 1)
            println(lcd)
            lcd shouldBe """
            >     - 
            >  | | |
            >     - 
            >  |   |
            >     - 
        """.trimMargin(marginPrefix = ">")
        }

        test("123456789 should print") {
            val lcd = c.nbToLCD(123456789, 1)
            println(lcd)
            lcd shouldBe """
            >     -   -       -   -   -   -   - 
            >  |   |   | | | |   |     | | | | |
            >     -   -   -   -   -       -   - 
            >  | |     |   |   | | |   | | |   |
            >     -   -       -   -       -   - 
        """.trimMargin(marginPrefix = ">")
        }

        test("2 with width 3 should be") {
            val lcd = c.nbToLCD(2, width = 3, height = 1)
            println(lcd)
            lcd shouldBe """
            > --- 
            >    |
            > --- 
            >|    
            > --- 
        """.trimMargin(marginPrefix = ">")
        }

        test("2 with width 3 and height 2 should be") {
            val lcd = c.nbToLCD(2, width = 3, height = 2)
            println(lcd)
            lcd shouldBe """
            > --- 
            >    |
            >    |
            > --- 
            >|    
            >|    
            > --- 
        """.trimMargin(marginPrefix = ">")
        }

        test("2 with width 3 and height 5 should be") {
            val lcd = c.nbToLCD(2, width = 3, height = 5)
            println(lcd)
            lcd shouldBe """
            > --- 
            >    |
            >    |
            >    |
            >    |
            >    |
            > --- 
            >|    
            >|    
            >|    
            >|    
            >|    
            > --- 
        """.trimMargin(marginPrefix = ">")
        }

        test("123456789 with width 10 and height 10 should be") {
            val lcd = c.nbToLCD(123456789, width = 10, height = 10)
            println(lcd)
            lcd shouldBe """
                >              ----------   ----------                ----------   ----------   ----------   ----------   ---------- 
                >           |            |            | |          | |            |                       | |          | |          |
                >           |            |            | |          | |            |                       | |          | |          |
                >           |            |            | |          | |            |                       | |          | |          |
                >           |            |            | |          | |            |                       | |          | |          |
                >           |            |            | |          | |            |                       | |          | |          |
                >           |            |            | |          | |            |                       | |          | |          |
                >           |            |            | |          | |            |                       | |          | |          |
                >           |            |            | |          | |            |                       | |          | |          |
                >           |            |            | |          | |            |                       | |          | |          |
                >           |            |            | |          | |            |                       | |          | |          |
                >              ----------   ----------   ----------   ----------   ----------                ----------   ---------- 
                >           | |                       |            |            | |          |            | |          |            |
                >           | |                       |            |            | |          |            | |          |            |
                >           | |                       |            |            | |          |            | |          |            |
                >           | |                       |            |            | |          |            | |          |            |
                >           | |                       |            |            | |          |            | |          |            |
                >           | |                       |            |            | |          |            | |          |            |
                >           | |                       |            |            | |          |            | |          |            |
                >           | |                       |            |            | |          |            | |          |            |
                >           | |                       |            |            | |          |            | |          |            |
                >           | |                       |            |            | |          |            | |          |            |
                >              ----------   ----------                ----------   ----------                ----------   ---------- 
                """.trimMargin(marginPrefix = ">")
        }
    }
})
