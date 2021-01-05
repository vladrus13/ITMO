import Test.HUnit
import Parser.Primitive
import Parser.Simple
import Parser.Parser
import Parser.Hard

test1_1 = TestCase (assertEqual "OK" (runParser ok "ok") (Just ((), "ok")))
test1_2 = TestCase (assertEqual "EOF" (runParser eof "") (Just ((), "")))
test1_3 = TestCase (assertEqual "EOF" (runParser eof "BAN") (Nothing))

test2_1 = TestCase (assertEqual "CBS" (runParser correctBracketSequence "()") (Just ((), "")))
test2_2 = TestCase (assertEqual "CBS super" (runParser correctBracketSequence "()(())((()))") (Just ((), "")))
test2_3 = TestCase (assertEqual "CBS ban" (runParser correctBracketSequence "()BAN") (Nothing))
test2_4 = TestCase (assertEqual "CBS not ended" (runParser correctBracketSequence "(()") (Nothing))

test2_5 = TestCase (assertEqual "Integer +" (runParser parseOnlyInt "+12") (Just ((12), "")))
test2_6 = TestCase (assertEqual "Integer -" (runParser parseOnlyInt "-12") (Just ((-12), "")))
test2_7 = TestCase (assertEqual "Integer  " (runParser parseOnlyInt "12") (Just ((12), "")))

test3_1 = TestCase (assertEqual "List parse 01" (runParser listlistParser "0") (Just ([[]], "")))
test3_2 = TestCase (assertEqual "List parse 02" (runParser listlistParser "1,0") (Just ([[0]], "")))
test3_3 = TestCase (assertEqual "List parse 03" (runParser listlistParser "2, 1,    1") (Just ([[1, 1]], "")))
test3_4 = TestCase (assertEqual "List parse 04" (runParser listlistParser "2, 1,+10  , 3,5,-7, 2") (Just([[1, 10], [5, -7, 2]], "")))

tests = TestList[TestLabel "OK" test1_1, TestLabel "EOF" test1_2, TestLabel "EOF" test1_3,
    TestLabel "CBS" test2_1, TestLabel "CBS super" test2_2, TestLabel "CBS ban" test2_3, TestLabel "CBS not ended" test2_4,
    TestLabel "Integer +" test2_5, TestLabel "Integer -" test2_6, TestLabel "Integer  " test2_7,
    TestLabel "List parse 01" test3_1, TestLabel "List parse 02" test3_2, TestLabel "List parse 03" test3_3, TestLabel "List parse 04" test3_4]

main :: IO Counts
main = runTestTT tests