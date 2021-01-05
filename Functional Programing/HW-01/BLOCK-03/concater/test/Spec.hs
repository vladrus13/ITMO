import Lib
import Test.HUnit
import Data.Monoid

test1_1 = TestCase (assertEqual "teacher's test" (maybeConcat [Just [1,2,3], Nothing, Just [4,5]]) [1,2,3,4,5])
test1_2 = TestCase (assertEqual "Empty test" (length (maybeConcat [Nothing, Nothing, Nothing])) 0)
test1_3 = TestCase (assertEqual "Big test" (maybeConcat [Just "Hello, ", Nothing, Just "Vlad! ", Nothing, Just "What about your FP homework? ",
    Nothing, Just "Did you work on advanced tasks? ", Just "Did you write full tests?"]) "Hello, Vlad! What about your FP homework? Did you work on advanced tasks? Did you write full tests?")

test2_1 = TestCase (assertEqual "teacher's test" (eitherConcat [Left (Sum 3), Right [1,2,3], Left (Sum 5), Right [4,5]]) (Sum {getSum = 8}, [1,2,3,4,5]))
test2_2 = TestCase (assertEqual "Big test" (eitherConcat [Right "Hello, ", Left "Hello, ", Right "World!", Left "World!"]) ("Hello, World!", "Hello, World!"))

tests = TestList[TestLabel "teacher's test" test1_1, TestLabel "Empty test" test1_2, TestLabel "Big test" test1_3,
    TestLabel "teacher's test" test2_1, TestLabel "Big test" test2_2]

main :: IO Counts
main = runTestTT tests