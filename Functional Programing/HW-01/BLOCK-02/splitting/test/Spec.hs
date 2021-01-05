import Lib
import Test.HUnit
import Data.List.NonEmpty

test1_1 = TestCase (assertEqual "path/to/file" (splitOn '/' "path/to/file") ("path" :| ["to", "file"]))
test1_2 = TestCase (assertEqual "ahahahahah" (splitOn 'h' "ahahahahah") ("a" :| ["a", "a", "a", "a", ""]))
test1_3 = TestCase (assertEqual "ke ke ke" (splitOn ' ' "ke ke ke") ("ke" :| ["ke", "ke"]))
test1_4 = TestCase (assertEqual "///" (splitOn '/' "///") ("" :| ["", "", ""]))

test2_1 = TestCase (assertEqual "path/to/file" "path/to/file" (joinWith '/' ("path" :| ["to", "file"])))
test2_2 = TestCase (assertEqual "ahahahahah" "ahahahahah" (joinWith 'h' ("a" :| ["a", "a", "a", "a", ""])))
test2_3 = TestCase (assertEqual "ke ke ke" "ke ke ke" (joinWith ' ' ("ke" :| ["ke", "ke"])))
test2_4 = TestCase (assertEqual "///" "///" (joinWith '/' ("" :| ["", "", ""])))

tests = TestList[TestLabel "path/to/file" test1_1, TestLabel "ahahahahah" test1_2, TestLabel "ke ke ke" test1_3, TestLabel "///" test1_4,
    TestLabel "path/to/file" test2_1, TestLabel "ahahahahah" test2_2, TestLabel "ke ke ke" test2_3, TestLabel "///" test2_4]

main :: IO Counts
main = runTestTT tests