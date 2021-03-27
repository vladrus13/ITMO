import Criterion.Main
import MonteCarlo.MonteCarlo
    ( consistentlyMonteCarlo, parrallelMonteCarlo )
import MonteCarlo.Expr
    ( Expr(ExprProduct, ExprPlus, ExprPower, ExprNum),
      ExprType(X, Number) )

-- | Count of points of Monte-Carlo algo
points :: Int 
points = 10000

-- | Start point
a :: Double
a = 0

-- | Finish point
b :: Double
b = 100000.89

-- | Polunomial expression
polynominal :: Expr ExprType
polynominal =
  ExprPlus
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

-- | Power 10000 expression
power10000 :: Expr ExprType
power10000 = ExprPower (ExprNum X) 10000

-- | Power 5000 exression
power5000 :: Expr ExprType
power5000 = ExprPower (ExprNum X) 5000

-- | Check x expression
xExpression :: (Expr ExprType -> Int -> Double -> Double -> Double) -> Double
xExpression function = function (ExprNum X) points a b

-- | Check polynomial expression
polynominalExpression :: (Expr ExprType -> Int -> Double -> Double -> Double) -> Double
polynominalExpression function = function polynominal points a b

-- | Check 10000 power expression
power10000Expression :: (Expr ExprType -> Int -> Double -> Double -> Double) -> Double
power10000Expression function = function power10000 points a b

-- | Check 5000 power expression
power5000Expression :: (Expr ExprType -> Int -> Double -> Double -> Double) -> Double
power5000Expression function = function power5000 points a b

-- | Main function for bench of monte-carlo algorithm
main :: IO ()
main =
  defaultMain [ 
    bgroup "monte-carlo" [
        bench "x-naive" $ nf xExpression consistentlyMonteCarlo,
        bench "x-parallel" $ nf xExpression parrallelMonteCarlo,
        bench "pol-naive" $ nf polynominalExpression consistentlyMonteCarlo,
        bench "pol-parallel" $ nf polynominalExpression parrallelMonteCarlo,
        bench "pow-10000-naive" $ nf power10000Expression consistentlyMonteCarlo,
        bench "pow-10000-parallel" $ nf power10000Expression parrallelMonteCarlo,
        bench "pow-5000-naive" $ nf power5000Expression consistentlyMonteCarlo,
        bench "pow-5000-parallel" $ nf power5000Expression parrallelMonteCarlo
        ]
    ]
