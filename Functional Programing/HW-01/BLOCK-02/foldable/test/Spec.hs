import Lib
import Test.HUnit

test1_1 = TestCase (assertEqual "add" (foldr (+) 0 (fromList [1, 2, 3])) 6)
test1_2 = TestCase (assertEqual "multiply" (foldr (*) 1 (fromList [1, 2, 3])) 6)
test1_3 = TestCase (assertEqual "0-multiply" (foldr (*) 0 (fromList [1, 2, 3])) 0)

tests = TestList[TestLabel "add" test1_1, TestLabel "multiply" test1_2, TestLabel "0-multiply" test1_3]

main :: IO Counts
main = runTestTT tests