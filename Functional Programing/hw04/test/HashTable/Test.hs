import Control.Monad (forM_)
import HashTable.HashTable (getCHT, newCHT, putCHT, sizeCHT)
import Test.HUnit

test_0_0 :: Test
test_0_0 =
  TestCase
    ( do
        table <- newCHT
        size <- sizeCHT table
        assertEqual "Empty table" 0 size
    )

test_0_1 :: Test
test_0_1 =
  TestCase
    ( do
        table <- newCHT
        putCHT "1" (1 :: Int) table
        putCHT "2" (2 :: Int) table
        putCHT "3" (3 :: Int) table
        size <- sizeCHT table
        assertEqual "Small table" 3 size
    )

test_0_2 :: Test
test_0_2 =
  TestCase
    ( do
        table <- newCHT
        putCHT "1" (1 :: Int) table
        putCHT "1" (2 :: Int) table
        putCHT "1" (3 :: Int) table
        size <- sizeCHT table
        assertEqual "Small table, but hash wider" 1 size
    )

test_0_3 :: Test
test_0_3 =
  TestCase
    ( do
        table <- newCHT
        forM_ ([1 .. 1000] :: [Int]) (\x -> putCHT (show x) x table)
        size <- sizeCHT table
        assertEqual "Big table, but hash wider" 1000 size
    )

sizeTests :: [Test]
sizeTests = [test_0_0, test_0_1, test_0_2, test_0_3]

test_1_0 :: Test
test_1_0 =
  TestCase
    ( do
        table <- newCHT
        putCHT "1" (1 :: Int) table
        putCHT "2" (2 :: Int) table
        putCHT "3" (3 :: Int) table
        element <- getCHT "2" table
        assertEqual "Get small table" (Just 2) element
    )

test_1_1 :: Test
test_1_1 =
  TestCase
    ( do
        table <- newCHT
        putCHT "1" (1 :: Int) table
        putCHT "2" (2 :: Int) table
        putCHT "3" (3 :: Int) table
        element <- getCHT "he-he-he" table
        assertEqual "Get nothing from small table" Nothing element
    )

test_1_2 :: Test
test_1_2 =
  TestCase
    ( do
        table <- newCHT
        forM_ ([1 .. 1000] :: [Int]) (\x -> putCHT (show x) x table)
        forM_
          ([1 .. 1000] :: [Int])
          ( \x -> do
              element <- getCHT (show x) table
              assertEqual ("Get " ++ show x) element (Just x)
          )
        size <- sizeCHT table
        assertEqual "Big table, but hash wider" 1000 size
    )

getTests :: [Test]
getTests = [test_1_0, test_1_1, test_1_2]

tests :: Test
tests = TestList (sizeTests ++ getTests)

main :: IO Counts
main = runTestTT tests