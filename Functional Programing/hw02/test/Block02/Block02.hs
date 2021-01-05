import Test.HUnit
import Expr
import SMA

test1_1 = TestCase (assertEqual "const 0" (eval (Const 0)) (Right 0))
test1_2 = TestCase (assertEqual "const 1" (eval (Const 1)) (Right 1))
test1_3 = TestCase (assertEqual "add 0 0" (eval (Sum (Const 0) (Const 0))) (Right 0))
test1_4 = TestCase (assertEqual "add 2 2" (eval (Sum (Const 2) (Const 2))) (Right 4))
test1_5 = TestCase (assertEqual "multiply 11 11" (eval (Multiply (Const 11) (Const 11))) (Right 121))
test1_6 = TestCase (assertEqual "divide -121 11" (eval (Divide (Const (-121)) (Const 11))) (Right (-11)))
test1_7 = TestCase (assertEqual "divide 1 0" (eval (Divide (Const 1) (Const 0))) (Left DivideByZero {message = "at expression (1 / 0)"}))
test1_8 = TestCase (assertEqual "power 2 10" (eval (Power (Const 2) (Const 10))) (Right 1024))
test1_9 = TestCase (assertEqual "power 2 -10" (eval (Power (Const 2) (Const (-10)))) (Left PowerByNegate {message = "at expression (2 ^ -10)"}))

test2_1 = TestCase (assertEqual "moving 1" (moving 4 [1, 5, 3, 8, 7, 9, 6]) ([1.0, 3.0, 3.0, 4.25, 5.75, 6.75, 7.5]))
test2_2 = TestCase (assertEqual "moving 2" (moving 2 [1, 5, 3, 8, 7, 9, 6]) ([1.0, 3.0, 4.0, 5.5, 7.5, 8.0, 7.5]))

tests = TestList[TestLabel "const 0" test1_1, TestLabel "const 1" test1_2, TestLabel "add 0 0" test1_3, TestLabel "add 2 2" test1_4, 
    TestLabel "multiply 11 11" test1_5, TestLabel "divide -121 11" test1_6, TestLabel "divide 1 0" test1_7, TestLabel "power 2 10" test1_8, 
    TestLabel "power 2 -10" test1_9, TestLabel "moving 1" test2_1, TestLabel "moving 2" test2_2]

main :: IO Counts
main = runTestTT tests