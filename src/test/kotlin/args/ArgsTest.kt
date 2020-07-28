package args

import Args
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.maps.beEmpty
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import java.lang.IllegalArgumentException

class ArgsTest : FunSpec({

    test("args class is init") {
        val args = Args("l:b;p:i;d:s;x:d")
        args shouldNot beNull()
        args.paramTypes shouldNot beEmpty()
        args.paramTypes["l"] shouldBe  Args.DataType.BOOLEAN
        args.paramTypes["p"] shouldBe  Args.DataType.INT
        args.paramTypes["d"] shouldBe  Args.DataType.STRING
        args.paramTypes["x"] shouldBe  Args.DataType.DOUBLE
    }

    context("Parse schema") {
        test("incorrect schema missing type should throw error") {
            shouldThrow<IllegalArgumentException> {
                Args("l:b;p;d:s")
            }
        }
        test("incorrect schema unknown type should throw error") {
            shouldThrow<IllegalArgumentException> {
                Args("l:b;p:w;d:s")
            }
        }
        test("incorrect schema no type should throw error") {
            shouldThrow<IllegalArgumentException> {
                Args("l:b;p;d:s")
            }
        }
    }

    context("Parse params") {
        val args = Args("l:b;p:i;d:s")
        args.parse("-l")
        args.params shouldNot beEmpty()
    }
})
