import Lib
import Test.HUnit

test1_1 = TestCase (assertEqual "add 0 0" (fromNum ((toNum 0) + (toNum 0))) (0))
test1_2 = TestCase (assertEqual "add 2 2" (fromNum ((toNum 2) + (toNum 2))) (4))
test1_3 = TestCase (assertEqual "add 10 0" (fromNum ((toNum 10) + (toNum 0))) (10))

test2_1 = TestCase (assertEqual "subtruct 0 0" (fromNum ((toNum 0) - (toNum 0))) (0))
test2_2 = TestCase (assertEqual "subtruct 2 2" (fromNum ((toNum 2) - (toNum 2))) (0))
test2_3 = TestCase (assertEqual "subtruct 10 9" (fromNum ((toNum 10) - (toNum 9))) (1))

test3_1 = TestCase (assertEqual "multiply 0 0" (fromNum ((toNum 0) * (toNum 0))) (0))
test3_2 = TestCase (assertEqual "multiply 5 5" (fromNum ((toNum 5) * (toNum 5))) (25))
test3_3 = TestCase (assertEqual "multiply 10 0" (fromNum ((toNum 10) * (toNum 0))) (0))

test4_1 = TestCase (assertEqual "equals 0 0" ((toNum 0) == (toNum 0)) (True))
test4_2 = TestCase (assertEqual "equals 10 0" ((toNum 10) == (toNum 0)) (False))
test4_3 = TestCase (assertEqual "equals 0 10" ((toNum 0) == (toNum 10)) (False))
test4_4 = TestCase (assertEqual "equals 10 10" ((toNum 10) == (toNum 10)) (True))

test5_1 = TestCase (assertEqual "/me 0 0" ((toNum 0) <= (toNum 0)) (True))
test5_2 = TestCase (assertEqual "/me 10 0" ((toNum 10) <= (toNum 0)) (False))
test5_3 = TestCase (assertEqual "/me 0 10" ((toNum 0) <= (toNum 10)) (True))
test5_4 = TestCase (assertEqual "/me 1 2" ((toNum 1) <= (toNum 2)) (True))

test6_1 = TestCase (assertEqual "IsEven 0" (Lib.isEven (toNum 0)) (True))
test6_2 = TestCase (assertEqual "IsEven 1" (Lib.isEven (toNum 1)) (False))
test6_3 = TestCase (assertEqual "IsEven 2" (Lib.isEven (toNum 2)) (True))

test7_2 = TestCase (assertEqual "Divide 10 2" (fromNum (Lib.div (toNum 10) (toNum 2))) (5))
test7_3 = TestCase (assertEqual "Divide 24 5" (fromNum (Lib.div (toNum 24) (toNum 5))) (4))

test8_2 = TestCase (assertEqual "Mod 10 2" (fromNum (Lib.mod (toNum 10) (toNum 2))) (0))
test8_3 = TestCase (assertEqual "Mod 24 5" (fromNum (Lib.mod (toNum 24) (toNum 5))) (4))

tests = TestList[TestLabel "add 0 0" test1_1, TestLabel "add 2 2" test1_2, TestLabel "add 10 0" test1_3,
    TestLabel "subtruct 0 0" test2_1, TestLabel "subtruct 2 2" test2_2, TestLabel "subtruct 10 2" test2_3,
    TestLabel "multiply 0 0" test3_1, TestLabel "multiply 5 5" test3_2, TestLabel "multiply 10 0" test3_3,
    TestLabel "equals 0 0" test4_1, TestLabel "equals 10 0" test4_2, TestLabel "equals 0 10" test4_3, TestLabel "equals 10 10" test4_4,
    TestLabel "/me 0 0" test5_1, TestLabel "/me 10 0" test5_2, TestLabel "/me 0 10" test5_3, TestLabel "/me 1 2" test5_4,
    TestLabel "IsEven 0" test6_1, TestLabel "IsEven 1" test6_2, TestLabel "IsEven 2" test6_2,
    TestLabel "Divide 10 2" test7_2, TestLabel "Divide 24 5" test7_3,
    TestLabel "Mod 10 2" test8_2, TestLabel "Mod 24 5" test8_3]

main :: IO Counts
main = runTestTT tests