{-# LANGUAGE BlockArguments #-}

import Test.HUnit
import Control.Monad (forM_)
import HalyavaScript.Script
import HalyavaScript.JavaScript

log2' :: Int -> Int 
log2' a = if a < 2 then 0 else log2' (a `div` 2) + 1

log2S :: (Script a, Monad a) => a (Int -> a Int)
log2S = sFun1 \a ->
    create (int (2)) \accum ->
    create (int (0)) \logCnt ->
    sWhile (a @>= getType accum) (
        accum @= getType accum @+ getType accum @#
        logCnt @= getType logCnt @+ up 1
    ) @#
    getType logCnt

log2SP :: (Script a) => a (Int -> a Int)
log2SP = sFun1 \a ->
    create (int 2) \accum ->
    create (int 0) \logCnt ->
    create (double 0.0) \_ ->
    create (string "abra") \_ ->
    sWhile (a @>= getType accum) (
        accum @= getType accum @+ getType accum @#
        logCnt @= getType logCnt @+ up 1
    ) @#
    getType logCnt

test_1_0 :: Test
test_1_0 =
  TestCase
    ( do
        forM_ ([1..1000]) (\x -> assertEqual ("Test " ++ show x) (log2' x) (runP (log2S @>>= up x)))
    )

runnerTests :: [Test]
runnerTests = [test_1_0]

log2JS :: String 
log2JS = "function f0(a) {\n  var v0 = 2;\n  var v1 = 0;\n  var v2 = 0.0;\n  var v3 = \"abra\";\n  while ((a) >= (x0)) {\n    x0 = (x0) + (x0);\n    x1 = (x1) + (1);}\n  return x1;\n}\n"

test_2_0 :: Test 
test_2_0 = TestCase (assertEqual "log2 code to js" log2JS (runR log2SP))

jsTests :: [Test]
jsTests = [test_2_0]

tests :: Test
tests = TestList (runnerTests ++ jsTests)

main :: IO Counts
main = runTestTT tests