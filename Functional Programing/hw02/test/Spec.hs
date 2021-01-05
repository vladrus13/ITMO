import StringSum
import Tree
import NonEmpty
import Test.HUnit
import System.Random
import Data.List

newRand = randomIO :: IO Int
randomList :: Int -> [Int]
randomList seed = randoms (mkStdGen seed) :: [Int]

list_1 = take 10 (randomList (12345))
list_2 = take 10000 (randomList (123454321))
toString :: [Int] -> String
toString x = intercalate " " $ map show x
toBadString :: [Int] -> String
toBadString x = (intercalate " " $ map show x) ++ (" BAD!!! AHAHAHAHAH!")

sumList :: [Int] -> Int
sumList [] = 0;
sumList (x : xs) = x + sumList xs

test1_1 = TestCase (assertEqual "sum 0" (stringSum "0") (Just 0))
test1_2 = TestCase (assertEqual "sum small" (stringSum "1 1") (Just 2))
test1_3 = TestCase (assertEqual "sum big" (stringSum "100 10 1 100 10 1 100 10 1") (Just 333))
test1_4 = TestCase (assertEqual "sum negative" (stringSum "-1 -1 -1") (Just (-3)))
test1_5 = TestCase (assertEqual "sum nothing" (stringSum "0 a 1") Nothing)

property_1 = TestCase (assertEqual "10 elements" (stringSum $ toString list_1) (Just $ sumList list_1))
property_2 = TestCase (assertEqual "10000 elements" (stringSum $ toString list_2) (Just $ sumList list_2))
property_3 = TestCase (assertEqual "10000 elements and bad ahahah" (stringSum $ toBadString list_2) (Nothing))

tests = TestList[TestLabel "sum 0" test1_1, TestLabel "sum small" test1_2, TestLabel "sum big" test1_3, TestLabel "sum negative" test1_4, TestLabel "sum nothing" test1_5,
    TestLabel "10 elements" property_1, TestLabel "10000 elements" property_2, TestLabel "10000 elements and bad ahahah" property_3]

main :: IO Counts
main = runTestTT tests