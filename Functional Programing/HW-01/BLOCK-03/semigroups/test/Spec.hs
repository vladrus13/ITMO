import Lib
import Test.HUnit

test1_1 = TestCase (assertEqual "Semigroup NonEmpty" ((<>) (5 :| [1, 2]) (10 :| [100, 1000])) (5 :| [1, 2, 10, 100, 1000]))
test1_2 = TestCase (assertEqual "NonEmpty Associative (local)" ((1 :| [2, 3]) <> (4 :| [5])) ((1 :| [2]) <> (3 :| [4, 5])))

test2_1 = TestCase (assertEqual "Semigroup TOT" ((This [(1 :: Int)]) <> (That [(2 :: Int)]) <> (This [(3 :: Int)]) <> 
    (That [(4 :: Int)])) (Both [(1 :: Int), (3 :: Int)] [(2 :: Int), (4 :: Int)]))
test2_2 = TestCase (assertEqual "Associative TOT" 
    (((Both [(1 :: Int)] [(2 :: Int)]) <> (Both [(3 :: Int)] [(4 :: Int)])) <> (Both [(5 :: Int)] [(6 :: Int)])) 
    ((Both [(1 :: Int)] [(2 :: Int)]) <> ((Both [(3 :: Int)] [(4 :: Int)]) <> (Both [(5 :: Int)] [(6 :: Int)]))))

test3_1 = TestCase (assertEqual "Name check" ((Name "root" <> Name "server") <> Name "user") (Name "root" <> (Name "server" <> Name "user")))

tests = TestList[TestLabel "Semigroup NonEmpty" test1_1, TestLabel "NonEmpty Associative" test1_2,
    TestLabel "Semigroup TOT" test2_1, TestLabel "Associative TOT" test2_2,
    TestLabel "Name check" test3_1]

main :: IO Counts
main = runTestTT tests