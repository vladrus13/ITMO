{-# LANGUAGE GADTs #-}

-- | Expression file
module MonteCarlo.Expr (ExprType (..), Expr (..), evaluate) where

-- | Expr type for Expr class
data ExprType
  = X
  | Number Double

-- | Evaluate type
evaluateType :: ExprType -> Double -> Double
evaluateType X a = a
evaluateType (Number x) _ = x

-- | Expression class
data Expr a where
  ExprNum :: ExprType -> Expr ExprType
  ExprPlus :: Expr ExprType -> Expr ExprType -> Expr ExprType
  ExprMinus :: Expr ExprType -> Expr ExprType -> Expr ExprType
  ExprProduct :: Expr ExprType -> Expr ExprType -> Expr ExprType
  ExprDivide :: Expr ExprType -> Expr ExprType -> Expr ExprType
  ExprSin :: Expr ExprType -> Expr ExprType
  ExprCos :: Expr ExprType -> Expr ExprType
  ExprTg :: Expr ExprType -> Expr ExprType
  ExprPower :: Expr ExprType -> Int -> Expr ExprType

-- | Power fuction
--
-- Created to test criterion, because have O(n) speed
power :: Double -> Int -> Double
power _ 0 = 1
power a b = power a (b - 1) * a

-- | Evaluate expression
evaluate :: Expr ExprType -> Double -> Double
evaluate (ExprNum a) x = evaluateType a x
evaluate (ExprPlus a b) x = evaluate a x + evaluate b x
evaluate (ExprMinus a b) x = evaluate a x - evaluate b x
evaluate (ExprProduct a b) x = evaluate a x * evaluate b x
evaluate (ExprDivide a b) x = evaluate a x / evaluate b x
evaluate (ExprSin a) x = sin (evaluate a x)
evaluate (ExprCos a) x = cos (evaluate a x)
evaluate (ExprTg a) x = tan (evaluate a x)
evaluate (ExprPower a b) x = power (evaluate a x) b