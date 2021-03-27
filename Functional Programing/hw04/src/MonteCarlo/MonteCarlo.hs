{-# LANGUAGE Strict #-}

-- | See https://en.wikipedia.org/wiki/Monte_Carlo_method
module MonteCarlo.MonteCarlo (parrallelMonteCarlo, consistentlyMonteCarlo) where

import Control.Monad.Par (runPar)
import Control.Monad.Par.Combinator (InclusiveRange (InclusiveRange), parMapReduceRange)
import MonteCarlo.Expr (Expr, ExprType, evaluate)

-- | Sum in Monte-Carlo formula parallel
sumOfMonteCarloParralel :: Expr ExprType -> Int -> Double -> Double -> Double
sumOfMonteCarloParralel expression count a b = runPar $ do
  let range = InclusiveRange 1 count
  let mapper x = return (evaluate expression (a + ((b - a) / fromIntegral count) * fromIntegral x))
  let reducer x y = return (x + y)
  parMapReduceRange range mapper reducer 0.0

-- | Sum in Monte-Carlo formula consistently
sumOfMonteCarlo :: Expr ExprType -> Int -> Double -> Double -> Double
sumOfMonteCarlo expression count a b = sum (map (\x -> evaluate expression (a + ((b - a) / fromIntegral count) * x)) [1, 2 .. fromIntegral count])

-- | Evaluate Monte-Carlo formula with given sum
monteCarlo :: (Expr ExprType -> Int -> Double -> Double -> Double) -> Expr ExprType -> Int -> Double -> Double -> Double
monteCarlo function expression count a b = (b - a) / fromIntegral count * function expression count a b

-- | Evaluate parallel Monte-Carlo formula
parrallelMonteCarlo :: Expr ExprType -> Int -> Double -> Double -> Double
parrallelMonteCarlo = monteCarlo sumOfMonteCarloParralel

-- | Evaluate consistently Monte-Carlo formula
consistentlyMonteCarlo :: Expr ExprType -> Int -> Double -> Double -> Double
consistentlyMonteCarlo = monteCarlo sumOfMonteCarlo