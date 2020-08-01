package args

import Args
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.beEmpty
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldHave
import io.kotest.matchers.shouldNot
import io.kotest.matchers.types.shouldBeTypeOf
import java.lang.IllegalArgumentException

class ArgsTest : FunSpec({

    test("args class is init") {
        val args = Args("l:b;p:i;d:s;x:d")
        args shouldNot beNull()
        args.paramTypes shouldNot beEmpty()
        args.paramTypes["l"] shouldBe Args.DataType.BOOLEAN
        args.paramTypes["p"] shouldBe Args.DataType.INT
        args.paramTypes["d"] shouldBe Args.DataType.STRING
        args.paramTypes["x"] shouldBe Args.DataType.DOUBLE
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

    context("Init default params") {
        test("When no args, params are set to default values") {
            val args = Args("l:b;p:i;d:s")
            val parsedArgs = args.parse()
            parsedArgs shouldHaveSize 3
            parsedArgs["l"] shouldBe false
            parsedArgs["p"] shouldBe 0
            parsedArgs["d"] shouldBe ""
        }

        test("When no param for double type default value is 0.0") {
            val args = Args("a:d")
            val parsedArgs = args.parse()
            parsedArgs["a"] shouldBe 0.0
        }
    }

    context("Parse params values") {
        test("Parse boolean should be set to true if exists") {
            val args = Args("l:b;p:i;d:s")
            val parsedArgs = args.parse("-l")
            parsedArgs shouldNot beEmpty()
            parsedArgs["l"] shouldBe true
            parsedArgs["p"] shouldBe 0
            parsedArgs["d"] shouldBe ""
        }

        test("Parse int should be set to its value") {
            val args = Args("l:b;p:i;d:s")
            val parsedArgs = args.parse("-p 12")
            parsedArgs shouldNot beEmpty()
            parsedArgs["p"] shouldBe 12
            parsedArgs["l"] shouldBe false
            parsedArgs["d"] shouldBe ""
        }

        test("Parse string should be set its value") {
            val args = Args("l:b;p:i;d:s")
            val parsedArgs = args.parse("-d hello")
            parsedArgs shouldNot beEmpty()
            parsedArgs["d"] shouldBe "hello"
            parsedArgs["p"] shouldBe 0
            parsedArgs["l"] shouldBe false
        }

        test("Parse double should be set its value") {
            val args = Args("a:d")
            val parsedArgs = args.parse("-a 12.34")
            parsedArgs shouldNot beEmpty()
            parsedArgs["a"] shouldBe 12.34
        }

        test("Parse multiple params should set right values") {
            val args = Args("l:b;p:i;d:s")
            val parsedArgs = args.parse("-l -p -588 -d /tmp/test/file.tmp")
            parsedArgs shouldNot beEmpty()
            parsedArgs["l"] shouldBe true
            parsedArgs["p"] shouldBe -588
            parsedArgs["d"] shouldBe "/tmp/test/file.tmp"
        }

        test("Unknown parameter should throw exception") {
            val args = Args("l:b;p:i;d:s")
            shouldThrow<IllegalArgumentException> {
                args.parse("-l -p 588 -d /tmp/test/file.tmp -s")
            }
        }

        test("Parse list of string should set its value") {
            val args = Args("a:ls")
            val parsedArgs = args.parse("-a this,is,a,test")
            parsedArgs shouldNot beEmpty()
            val argA = parsedArgs["a"] as List<*>
            argA shouldHaveSize 4
            argA shouldContainExactly listOf("this", "is", "a", "test")
        }

        test("Parse list of int should set its value") {
            val args = Args("a:li")
            val parsedArgs = args.parse("-a 3,55,9767")
            parsedArgs shouldNot beEmpty()
            val argA = parsedArgs["a"] as List<*>
            argA shouldHaveSize 3
            argA shouldContainExactly listOf(3, 55, 9767)
        }

        test("Parse list of double should set its value") {
            val args = Args("a:ld")
            val parsedArgs = args.parse("-a 3.4,55.66,9767.67")
            parsedArgs shouldNot beEmpty()
            val argA = parsedArgs["a"] as List<*>
            argA shouldHaveSize 3
            argA shouldContainExactly listOf(3.4, 55.66, 9767.67)
        }
    }
})
