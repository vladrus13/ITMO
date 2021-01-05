import Lib
import Test.HUnit

import qualified Data.List.NonEmpty as NONEMPTY

test1_1 = TestCase (assertEqual "Nil is empty test" (isEmpty (Nil)) True)
test1_2 = TestCase (assertEqual "List is empty test" (isEmpty (Node (NONEMPTY.fromList [1]) Nil Nil)) False)

test2_1 = TestCase (assertEqual "Size of nil" (size Nil) 0)
test2_2 = TestCase (assertEqual "Size of Tree" (size (Node (NONEMPTY.fromList [1]) (Node (NONEMPTY.fromList [0]) Nil Nil) (Node (NONEMPTY.fromList [2]) Nil Nil))) 3)

test1_5_1 = TestCase (assertEqual "Find left child" (find (Node (NONEMPTY.fromList [1]) (Node (NONEMPTY.fromList [0]) Nil Nil) (Node (NONEMPTY.fromList [2]) Nil Nil)) 0) (Just (Node (NONEMPTY.fromList [0]) Nil Nil)))
test1_5_2 = TestCase (assertEqual "Find leftest child" (find (Node (NONEMPTY.fromList [1]) 
    (Node (NONEMPTY.fromList [0]) Nil Nil) (Node ((NONEMPTY.fromList [2])) Nil 
        (Node ((NONEMPTY.fromList [3])) Nil (Node (NONEMPTY.fromList [4]) Nil Nil)))) 4) (Just (Node (NONEMPTY.fromList [4]) Nil Nil)))


test3_1 = TestCase (assertEqual "Insert" (toList (insert (Node (NONEMPTY.fromList [1]) Nil (Node (NONEMPTY.fromList [3]) Nil Nil)) 2)) [1, 2, 3])
test3_2 = TestCase (assertEqual "Insert more" (toList (insert (insert (insert (insert (Nil) 6) 4) 1) 3)) [1, 3, 4, 6])

test4_1 = TestCase (assertEqual "from-to list" (toList (fromList [1, 2, 4, 3, 5, 6])) [1, 2, 3, 4, 5, 6])

test5_1 = TestCase (assertEqual "remove elements" (toList (remove (remove (remove (fromList ([1, 2, 3, 4, 5, 6])) 2) 4) 6)) [1, 3, 5])
test5_2 = TestCase (assertEqual "remove element" (toList (remove (fromList [2]) 1)) [2])

tests = TestList[TestLabel "Nil Empty" test1_1, TestLabel "Nil Empty" test1_1, 
    TestLabel "Size of nil" test2_1, TestLabel "Size of Tree" test2_2,
    TestLabel "Find left child" test1_5_1, TestLabel "Find leftest" test1_5_2,
    TestLabel "Insert 2 to 1 3" test3_1, TestLabel "Insert hard" test3_2,
    TestLabel "from-to list" test4_1,
    TestLabel "remove tests" test5_1, TestLabel "remove element" test5_2]

main :: IO Counts
main = runTestTT tests