import MonteCarlo.Expr
import MonteCarlo.MonteCarlo
import Test.HUnit

eps :: Double
eps = 0.001

checkToEps :: Double -> Double -> Bool
checkToEps a b = abs (a - b) < eps

test_0_0 :: Test
test_0_0 = TestCase $ assertEqual "evaluate" 2 (evaluate (ExprNum $ Number 2) 100)

test_0_1 :: Test
test_0_1 = TestCase $ assertEqual "evaluate" 1024 (evaluate (ExprPower (ExprNum X) 10) 2)

testsEvaluate :: [Test]
testsEvaluate = [test_0_0, test_0_1]

test_1_0 :: Test
test_1_0 = TestCase $ assertBool "naive monte" (checkToEps 2 (consistentlyMonteCarlo (ExprNum X) 100000 0 2))

test_1_1 :: Test
test_1_1 = TestCase $ assertEqual "naive monte" 100 (consistentlyMonteCarlo (ExprNum $ Number 1) 100000 0 100)

test_1_2 :: Test
test_1_2 =
  TestCase $
    assertBool
      "naive monte"
      ( checkToEps
          (5299 / 20)
          ( consistentlyMonteCarlo
              ( ExprPlus
                  (ExprProduct (ExprNum $ Number 1) (ExprPower (ExprNum X) 4))
                  ( ExprPlus
                      (ExprProduct (ExprNum $ Number 2) (ExprPower (ExprNum X) 3))
                      ( ExprPlus
                          (ExprProduct (ExprNum $ Number 3) (ExprPower (ExprNum X) 2))
                          ( ExprPlus
                              (ExprProduct (ExprNum $ Number 4) (ExprPower (ExprNum X) 1))
                              (ExprNum $ Number 5)
                          )
                      )
                  )
              )
              1000000
              0
              3.5
          )
      )

testsNaive :: [Test]
testsNaive = [test_1_0, test_1_1, test_1_2]

test_2_0 :: Test
test_2_0 = TestCase $ assertBool "fast monte" (checkToEps 2 (parrallelMonteCarlo (ExprNum X) 100000 0 2))

test_2_1 :: Test
test_2_1 = TestCase $ assertEqual "fast monte" 100 (parrallelMonteCarlo (ExprNum $ Number 1) 100000 0 100)

test_2_2 :: Test
test_2_2 =
  TestCase $
    assertBool
      "fast monte"
      ( checkToEps
          (5299 / 20)
          ( parrallelMonteCarlo
              ( ExprPlus
                  (ExprProduct (ExprNum $ Number 1) (ExprPower (ExprNum X) 4))
                  ( ExprPlus
                      (ExprProduct (ExprNum $ Number 2) (ExprPower (ExprNum X) 3))
                      ( ExprPlus
                          (ExprProduct (ExprNum $ Number 3) (ExprPower (ExprNum X) 2))
                          ( ExprPlus
                              (ExprProduct (ExprNum $ Number 4) (ExprPower (ExprNum X) 1))
                              (ExprNum $ Number 5)
                          )
                      )
                  )
              )
              1000000
              0
              3.5
          )
      )

testsFast :: [Test]
testsFast = [test_2_0, test_2_1, test_2_2]

tests :: Test
tests = TestList (testsEvaluate ++ testsNaive ++ testsFast)

main :: IO Counts
main = runTestTT tests